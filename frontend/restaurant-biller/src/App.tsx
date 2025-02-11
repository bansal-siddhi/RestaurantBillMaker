import React, { useState, useEffect } from 'react';

import { jsPDF } from "jspdf";

function App() {
    const [menuItems, setMenuItems] = useState([]);
    const [order, setOrder] = useState({});
    const [bill, setBill] = useState(null);
    const [error, setError] = useState(null);
    const API_BASE_URL = 'http://localhost:8080';

    const GST_RATE = 0.05;

    useEffect(() => {
        const fetchMenu = async () => {
            try {
                const response = await fetch(`${API_BASE_URL}/menu/menuItems`);
                console.log(response);
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();
                setMenuItems(data);
            } catch (err) {
                setError(err.message);
            }
        };

        fetchMenu();
    }, []);

    const handleAddToOrder = (item) => {
        setOrder({ ...order, [item.id]: (order[item.id] || 0) + 1 });
    };

    const handleRemoveFromOrder = (item) => {
        const newOrder = {...order};
        if (newOrder[item.id] > 0) {
            newOrder[item.id]--;
            if (newOrder[item.id] === 0) {
                delete newOrder[item.id];
            }
        }
        setOrder(newOrder);
    }

const calculateSubtotal = () => {
        let subtotal = 0;
        for (const itemId in order) {
            const item = menuItems.find((item) => item.id === parseInt(itemId));
            if (item) {
                subtotal += item.itemPrice * order[itemId];
            }
        }
        return subtotal.toFixed(2);
    };

    const calculateGST = () => {
            const subtotal = calculateSubtotal();
            return (subtotal * GST_RATE).toFixed(2);  // GST calculation
        };

        const calculateTotal = () => {
            const subtotal = calculateSubtotal();
            const gst = calculateGST();
            return (parseFloat(subtotal) + parseFloat(gst)).toFixed(2);
        };
    const generateBillPdf = () => {
        try {
            const doc = new jsPDF();
            const restaurantName = "Mumma's Kitchen BLR.";
            const today = new Date();

            // Add Restaurant Name and Bill Title
            doc.setFontSize(18);
            doc.text(restaurantName, 20, 20);
            doc.setFontSize(16);
            doc.text("Dining Bill", 20, 30);
            doc.setFontSize(10);
            doc.text(`Date: ${today.toLocaleDateString()}`, 160, 20);
            doc.text(`Time: ${today.toLocaleTimeString()}`, 160, 25);

            let yOffset = 40;

            // Add header for itemized list
            doc.setFontSize(12);
            doc.text('Item Name', 20, yOffset);
            doc.text('Quantity', 100, yOffset);
            doc.text('Price', 140, yOffset);
            yOffset += 10;

            doc.setLineWidth(0.5);
            doc.line(20, yOffset, 190, yOffset);
            yOffset += 5;

            // Add order details in a table format
            Object.keys(order).forEach((itemId) => {
                const item = menuItems.find((item) => item.id === parseInt(itemId));
                if (item) {
                    doc.text(item.itemName, 20, yOffset);
                    doc.text(order[itemId].toString(), 100, yOffset);
                    doc.text(`${(item.itemPrice * order[itemId]).toFixed(2)}`, 140, yOffset);
                    yOffset += 10;
                }
            });

            // Add a line after the item list
            doc.line(20, yOffset, 190, yOffset);
            yOffset += 5;

            // Add Subtotal, GST, and Total with proper spacing
            const subtotal = calculateSubtotal();
            const gst = calculateGST();
            const totalAmount = calculateTotal();

            doc.text(`Subtotal: ${subtotal}`, 20, yOffset);
            yOffset += 10;
            doc.text(`GST (5%): ${gst}`, 20, yOffset);
            yOffset += 10;
            doc.text(`Total Amount: ${totalAmount}`, 20, yOffset);

            // Add footer with restaurant contact details
            yOffset += 15;
            doc.setFontSize(8);
            doc.text("Thank you for dining with us!", 20, yOffset);
            doc.text("For queries, contact us at: contact@myrestaurant.com", 20, yOffset + 5);

            return doc;
        } catch (err) {
            setError(err.message);
        }
    };

    // Save PDF
    const handleSaveBill = () => {
        const doc = generateBillPdf(); // Generate the bill PDF
        if (doc) {
            doc.save('bill.pdf'); // Save the bill as a PDF file
        }
    };

    // Print PDF
    const handlePrintBill = () => {
        const doc = generateBillPdf(); // Generate the bill PDF
        if (doc) {
            const pdfUrl = doc.output('bloburl');
            const printWindow = window.open(pdfUrl);
            printWindow.onload = function() {
                printWindow.print(); // Open print dialog
            };
        }
    };


    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className="app-container">
            <h1>Restaurant Biller</h1>

            <h2>Menu</h2>
            <ul className="menu-list">
                {menuItems.map((item) => (
                    <li key={item.id} className="menu-item">
                        <span>{item.itemName} - Rs {item.itemPrice}</span>
                        <button onClick={() => handleAddToOrder(item)}>+</button>
                        <button onClick={() => handleRemoveFromOrder(item)}>-</button>
                    </li>
                ))}
            </ul>

            <h2>Your Order</h2>
            {Object.keys(order).length === 0 ? (
                <p>No items in your order yet.</p>
            ) : (
                <ul>
                    {Object.keys(order).map((itemId) => {
                        const item = menuItems.find((item) => item.id === parseInt(itemId));
                        return (
                            <li key={itemId}>
                                {item.itemName} x {order[itemId]} - {item.itemPrice * order[itemId]}
                            </li>
                        );
                    })}
                    <p>Total: Rs {calculateTotal()}</p>
                </ul>
            )}
            <button onClick={handleSaveBill} disabled={Object.keys(order).length === 0}>
                        Save Bill
                    </button>
                    <button className="bg-red-300" onClick={handlePrintBill} disabled={Object.keys(order).length === 0}>
                        Print Bill
                    </button>
            {bill && (
                <div className="bill-container">
                    <h2>Bill</h2>
                    <p>Bill Number: {bill.billNumber}</p>
                    <p>Subtotal: {bill.subTotal}</p>
                    <p>Tax: {bill.tax}</p>
                    <p>Total Amount: {bill.totalAmount}</p>
                </div>
            )}
        </div>
    );
}

export default App;