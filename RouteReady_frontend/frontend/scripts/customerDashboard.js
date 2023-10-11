const apiUrl = 'http://localhost:8088/customers/';
const userId = localStorage.getItem('userId'); // Retrieve customer ID from localStorage 
const userName = localStorage.getItem("userName");
const jwtToken = localStorage.getItem("jwtToken");
console.log(userName, userId, jwtToken);
// Code for display User Name
document.getElementById("userName").textContent = userName;

//Code for redirect to home page
let homepage = document.getElementById("nav-logo-img");
homepage.addEventListener("click", () => {
    window.location.href="./index.html"
}
);



// // Function to display the selected content
function displayContent(option) {
    const content = document.querySelector('.content');

    // Clear previous content
    content.innerHTML = '';

    // Render the selected content
    switch (option) {
        case 'update-customer':
            content.innerHTML = `
            <h2 style="text-align: center">Update Customer</h2>
            <form id="inputform" style="width: 25%; gap: 10px; margin: 10px auto; display: flex; flex-direction: column; padding: 20px; border: 2px solid #ccc; background-color: #f9f9f9;"     onsubmit="updateCustomer(event)" >
                <label for="customerName">Customer Name</label>
                <input type="text" id="customerName" name="customerName">
                <label for="customerEmail">Email</label>
                <input type="email" id="customerEmail" name="customerEmail">
                <button type="submit" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Update</button>
            </form>
        `;

            break;
        case 'insert-trip-booking':
            content.innerHTML = `
                <h2 style="text-align: center">Trip Booking</h2>
                <form id="tripForm" style="width: 25%; gap: 10px; margin: 10px auto; display: flex; flex-direction: column; padding: 20px; border: 2px solid #ccc; background-color: #f9f9f9;" onsubmit="insertTripBooking(event)">
                    <label for="fromLocation">From Location</label>
                    <input type="text" id="fromLocation" name="fromLocation">
                    <label for="toLocation">To Location</label>
                    <input type="text" id="toLocation" name="toLocation">
                    <label for="fromDateTime">From Booking Date</label>
                    <input type="datetime-local" id="fromDateTime" name="fromDateTime">
                    <label for="toDateTime">To Booking Date</label>
                    <input type="datetime-local" id="toDateTime" name="toDateTime">
                    <label for="distanceInKm">Distance (in Km):</label>
                    <input type="number" id="distanceInKm" name="distanceInKm" required>
                    <br><br>
                    <button type="submit" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Book Trip</button>
                </form>
            `;

            break;

        case 'update-trip-booking':
            content.innerHTML = `
    <h2 style="text-align: center">Update Trip</h2>
    <form id="tripForm" style="width: 25%; gap: 10px; margin: 10px auto; display: flex; flex-direction: column; padding: 20px; border: 2px solid #ccc; background-color: #f9f9f9;"  onsubmit="updateTripBooking(event)">
        <label for="bookingId">Booking ID</label>
        <input type="text" id="bookingId" name="bookingId">
        <label for="newFromLocation">New From Location</label>
        <input type="text" id="newFromLocation" name="newFromLocation">
        <label for="newToLocation">New To Location</label>
        <input type="text" id="newToLocation" name="newToLocation">
        <label for="newFromDate">New Booking Date</label>
        <input type="datetime-local" id="newFromDate" name="newFromDate">
        <label for="newToDate">New Booking Date</label>
        <input type="datetime-local" id="newToDate" name="newToDate">
        <label for="distanceInKm">Distance (in Km):</label>
                    <input type="number" id="distanceInKm" name="distanceInKm" required>
                    <br><br>
        <button type="submit" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Update Booking</button>
    </form>
`;

            break;
        case 'delete-trip-booking':
            content.innerHTML = `
            <h2 style="text-align: center">Delete Trip</h2>
            <form id="tripForm" style="width: 25%; gap: 10px; margin: 10px auto; display: flex; flex-direction: column; padding: 20px; border: 2px solid #ccc; background-color: #f9f9f9;" onsubmit="deleteTripBooking(event)">
                <label for="bookingId">Booking ID</label>
                <input type="number" id="bookingId" name="bookingId">
                <button type="submit" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Delete Booking</button>
            </form>
        `;

            break;

        default:
            content.innerHTML = '<h2>Welcome, Customer!</h2><p>Select an option from the sidebar to view the results.</p>';
    }
}
//Code for User Trip history
const table = document.getElementById('tripTable');
const tripApi = 'http://localhost:8088/tripbookings/customer/'
async function fetchData() {
    try {
        const headers = {
            'Authorization': 'Bearer ' + jwtToken,
            'Content-Type': 'application/json',
        };
        const response = await fetch(tripApi + userId, {
            headers: headers,
        });
        const data = await response.json();
        console.log(data)
        data.forEach(entry => {
            const row = document.createElement('tr');

            const keys = [
                'tripBookingId', 'fromLocation', 'toLocation', 'fromDateTime', 'toDateTime',
                'distanceInKm', 'driver.cab.perKmRate', 'bill', 'driver.name', 'driver.mobileNumber', 'driver.email',
                'driver.cab.carType'
            ];

            keys.forEach(key => {
                const cell = document.createElement('td');
                let value = entry;
                key.split('.').forEach(part => {
                    value = value[part];
                });
                if (value === null || value === undefined) {
                    value = 'N/A';
                }
                cell.textContent = value;
                row.appendChild(cell);
            });

            table.appendChild(row);
        });
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}
fetchData();

// Function for Trip Booking 
function insertTripBooking(event) {
    event.preventDefault();
    const fromLocation = document.getElementById('fromLocation').value;
    const toLocation = document.getElementById('toLocation').value;
    const fromDateTime = document.getElementById('fromDateTime').value;
    const toDateTime = document.getElementById('toDateTime').value;
    const distanceInKm = parseInt(document.getElementById("distanceInKm").value);

    const tripData = {
        "fromLocation": fromLocation,
        "toLocation": toLocation,
        "fromDateTime": fromDateTime,
        "toDateTime": toDateTime,
        "distanceInKm": distanceInKm
    };


    var requestOptions = {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + jwtToken,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(tripData),
        redirect: 'follow'
    };

    console.log(tripData, "tripdata")
    fetch("http://localhost:8088/tripbookings/" + userId, requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
    // Reset form fields
    // clearForm();
}

// Function for update Trip
function updateTripBooking(event) {
    event.preventDefault();

    const bookingId = document.getElementById('bookingId').value;
    const newFromLocation = document.getElementById('newFromLocation').value;
    const newToLocation = document.getElementById('newToLocation').value;
    const newFromDate = document.getElementById('newFromDate').value;
    const newToDate = document.getElementById('newToDate').value;
    const distance = document.getElementById("distanceInKm").value;


    // Construct the updated trip booking object
    const updatedData = {
        tripId: bookingId,
        fromLocation: newFromLocation,
        toLocation: newToLocation,
        fromDateTime: newFromDate,
        toDateTime: newToDate,
        distanceInKm: distance

    };
    console.log(updatedData);
    // Make a PUT request to update the trip booking
    fetch("http://localhost:8088/tripbookings/update", {
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer ' + jwtToken,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedData)
    })
        .catch(error => {
            console.error('An error occurred while updating trip booking:', error);
            // Optionally, you can display an error message or perform any other action
        });

    document.getElementById("tripForm").reset();

}
// Function for delete Trip
function deleteTripBooking(event) {
    event.preventDefault();

    const bookingId = document.getElementById('bookingId').value;
    console.log(bookingId)
    // Make a DELETE request to delete the trip booking
    fetch(`http://localhost:8088/tripbookings/${bookingId}`, {
        method: 'DELETE',
        headers: {
            // 'Authorization': 'Bearer ' + jwtToken,
            'Authorization':'Bearer '+jwtToken,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            // Handle the response
            if (response.ok) {
                // Display success message
                alert('Trip with id :'+bookingId+" Deleted Successfully")
                console.log('Trip booking deleted successfully!');
                clearForm();
                // Optionally, you can update the UI or perform any other action
            } else {
                // Handle errors
                console.error('Failed to delete trip booking.');
                // Optionally, you can display an error message or perform any other action
            }
        })
        .catch(error => {
            console.error('An error occurred while deleting trip booking:', error);
            // Optionally, you can display an error message or perform any other action
        });
}
// Code for making logout
document.getElementById("logout-button").addEventListener("click", confirmLogout);
// Function for confirm logout
function confirmLogout() {
    if (confirm("Are you sure you want to logout?")) {
        logoutCustomer();
    }
}
//Function for making logout
function logoutCustomer() {
    localStorage.clear();
    window.location.href = 'index.html';
}



// Function for update Customer Details
async function updateCustomer(event) {
    event.preventDefault();
    const name = document.getElementById("customerName").value;
    const email = document.getElementById("customerEmail").value;
    const customerData = {
        "customerName": name,
        "customerEmail": email
    };
    console.log(customerData)

    try {
        const response = await fetch(`${apiUrl}customer/${userId}`, {
            method: 'PATCH',
            headers: {
                'Authorization': 'Bearer ' + jwtToken,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(customerData),
        });

        if (!response.ok) {
            throw new Error('Failed to update customer');
        }

        const updatedCustomer = await response.json();
        document.getElementById("inputform").reset();
        return updatedCustomer;
    } catch (error) {

        console.error('Error updating customer:', error);
        throw error;
    }

}
//Function for clear form
function clearForm(){
    document.getElementById("tripForm").reset();
}