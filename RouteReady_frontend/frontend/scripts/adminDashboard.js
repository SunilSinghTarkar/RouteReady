const apiUrl = 'http://localhost:8088/admins/';

const userId = localStorage.getItem('userId'); // Retrieve customer ID from localStorage 
const userName = localStorage.getItem("userName");
const jwtToken = localStorage.getItem("jwtToken");

let homepage = document.getElementById("nav-logo-img");
homepage.addEventListener("click", () => {
  logoutCustomer();
}
);
// Function to display the selected content
function displayContent(option) {
  const content = document.querySelector('.content');

  // Clear previous content
  content.innerHTML = '';

  // Render the selected content
  switch (option) {
    case 'add-admin':
      content.innerHTML = `
        <h2 style = "text-align : center">Add new admin</h2>
        <form id="inputform" style="width: 25%; gap: 10px; margin: 10px auto; display: flex; flex-direction: column; padding: 20px; border: 2px solid #ccc; background-color: #f9f9f9;"     onsubmit="addAdmin(event)" >
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
     
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required>
     
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
      
        <label for="mobile">Mobile Number:</label>
        <input type="text" id="mobile" name="mobile" required>
   
        <label for="username">Username:</label>
        <input type="text" id="customer-username" name="username" required>
     
        <label for="password">Password:</label>
        <input type="password" id="customer-password" name="password" required>
          <button type="submit" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Add Admin</button>
        </form>
      `;
      break;
    case 'update-admin':
      content.innerHTML = `
        <h2 style = "text-align : center">Update admin</h2>
        <form style="width: 30% ;gap : 10px; margin: 10px auto ; display: flex; flex-direction: column;">
          <label for="adminId">Admin ID</label>
          <input type="text" id="adminId" name="adminId">
          <label for="adminName">New Admin Name</label>
          <input type="text" id="adminName" name="adminName">
          <label for="adminEmail">New Admin Email</label>
          <input type="email" id="adminEmail" name="adminEmail">
          <button type="submit" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Update Admin</button>
        </form>
      `;
      break;
    case 'delete-admin':
      content.innerHTML = `
        <h2 style = "text-align : center">Delete admin</h2>
        <form style="width: 30% ;gap : 10px; margin: 10px auto ; display: flex; flex-direction: column;">
          <label for="adminId">Admin ID</label>
          <input type="text" id="adminId" name="adminId">
          <button type="submit" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Delete Admin</button>
        </form>
      `;
      break;
    case 'get-all-trips':
      content.innerHTML = '<h2 style = "text-align : center">List of All Trips</h2>';
      window.location.reload();
      break;
    case 'get-trips-cab-wise':
      content.innerHTML = `
        <h2 style = "text-align : center">Get trips cab wise</h2>
        <div style="width: 30% ;gap : 10px; margin: 10px auto ; display: flex; flex-direction: column;">
          <label for="cabId">Enter Cab ID: </label>
          <input type="text" id="cabId" />
          <button onclick="getAllTripsCabwise()" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Get Trips</button>
        </div>
        <div id="tripTable"></div>
      `;
      break;
    case 'get-trips-customer-wise':
      content.innerHTML = `
        <h2 style = "text-align : center">Get trips customer wise</h2>
        <div style="width: 30% ;gap : 10px; margin: 10px auto ; display: flex; flex-direction: column;">
          <label for="customerId">Enter Customer ID: </label>
          <input type="text" id="customerId" />
          <button onclick="getAllTripsCustomerWise()" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Get Trips</button>
        </div>
        <div id="tripTable"></div>
      `;
      break;
    case 'get-trips-by-date':
      content.innerHTML = `
          <h2 style = "text-align : center">Get trips by date range</h2>
          <div style="width: 30% ;gap : 10px; font-size: 23px; display: flex; flex-direction: column; align-items: center; margin: 10px auto;">
            <div>
              <label for="startDate">Start Date: </label>
              <input type="date" id="startDate" />
            </div>
            <div>
              <label for="endDate">End Date: </label>
              <input type="date" id="endDate" />
            </div>
            <div>
              <button onclick="getTripsByDate()" style="font-size: 23px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Get Trips</button>
            </div>
          </div>
          
          <div id="tripTable"></div>
        `;
    case 'get-querys':
      content.innerHTML = `
          <h2 style = "text-align : center">Customer Querys</h2>
          <table id="customerTable">
        <thead>
            <tr>
                <th>Query ID</th>
                <th>Name</th>
                <th>State</th>
                <th>Message</th>
                <th>Open</th>
            </tr>
        </thead>
        <tbody>
            <!-- Table content will be dynamically added here -->
        </tbody>
    </table>
        `;
      fetchAndDisplayData();
      break;
    default:
      content.innerHTML = '<h2 style = "text-align : center">Welcome, Admin!</h2><p>Select an option from the sidebar to view the results.</p>';
  }
}

// ***********************************************************************************************************
// Function to fetch all trips and display in a table
const table = document.getElementById('allTripsTable');
async function getAllTrips() {
  try {
    const headers = {
      'Authorization': 'Bearer ' + jwtToken,
      'Content-Type': 'application/json',
  };
    const response = await fetch(apiUrl+"alltripbookings", {
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

getAllTrips();

// ***********************************************************************************************************
// Function to fetch all trips and display in a table
function getAllTripsCabwise() {

  const tripsByCab = [
    {
      tripBookingId: 2,
      fromLocation: 'Location 3',
      toLocation: 'Location 4',
      fromDateTime: '2023-06-16 09:00:00',
      toDateTime: '2023-06-16 11:30:00',
      status: 'Cancelled',
      distanceInKm: 15.2,
      bill: 180.25
    },
    // Add more trip objects as needed
  ];

  // Create the table element and apply borders
  const table = document.createElement('table');
  table.classList.add('trip-table');
  table.style.borderCollapse = 'collapse';
  table.style.border = '1px solid black';

  // Create the table header
  const thead = document.createElement('thead');
  const headerRow = document.createElement('tr');
  headerRow.innerHTML = `
    <th>Trip ID</th>
    <th>From Location</th>
    <th>To Location</th>
    <th>Cab</th>
    <!-- Add more table header cells as needed -->
  `;
  thead.appendChild(headerRow);
  table.appendChild(thead);

  // Create the table body
  const tbody = document.createElement('tbody');

  // Iterate over the trips data and create table rows
  tripsByCab.forEach(trip => {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${trip.tripBookingId}</td>
      <td>${trip.fromLocation}</td>
      <td>${trip.toLocation}</td>
      <td>${trip.cab}</td>
      <!-- Add more table cells as needed -->
    `;
    tbody.appendChild(row);
  });

  // Append the table body to the table
  table.appendChild(tbody);

  // Add border to the table cells
  const cells = table.querySelectorAll('td, th');
  cells.forEach(cell => {
    cell.style.border = '1px solid black';
    cell.style.padding = '8px';
  });

  // Append the table to the content section
  const content = document.querySelector('.content');
  content.appendChild(table);
}


// ***********************************************************************************************************
// Function to fetch all trips and display in a table
function getAllTripsCustomerWise() {

  const tripsByCustomer = [
    {
      tripBookingId: 2,
      fromLocation: 'Location 3',
      toLocation: 'Location 4',
      fromDateTime: '2023-06-16 09:00:00',
      toDateTime: '2023-06-16 11:30:00',
      status: 'Cancelled',
      distanceInKm: 15.2,
      bill: 180.25
    },
    // Add more trip objects as needed
  ];

  // Create the table element and apply borders
  const table = document.createElement('table');
  table.classList.add('trip-table');
  table.style.borderCollapse = 'collapse';
  table.style.border = '1px solid black';

  // Create the table header
  const thead = document.createElement('thead');
  const headerRow = document.createElement('tr');
  headerRow.innerHTML = `
    <th>Trip ID</th>
    <th>From Location</th>
    <th>To Location</th>
    <th>Customer</th>
    <!-- Add more table header cells as needed -->
  `;
  thead.appendChild(headerRow);
  table.appendChild(thead);

  // Create the table body
  const tbody = document.createElement('tbody');

  // Iterate over the trips data and create table rows
  tripsByCustomer.forEach(trip => {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${trip.tripBookingId}</td>
      <td>${trip.fromLocation}</td>
      <td>${trip.toLocation}</td>
      <td>${trip.customer}</td>
      <!-- Add more table cells as needed -->
    `;
    tbody.appendChild(row);
  });

  // Append the table body to the table
  table.appendChild(tbody);

  // Add border to the table cells
  const cells = table.querySelectorAll('td, th');
  cells.forEach(cell => {
    cell.style.border = '1px solid black';
    cell.style.padding = '8px';
  });

  // Append the table to the content section
  const content = document.querySelector('.content');
  content.appendChild(table);
}

// ***********************************************************************************************************************
document.getElementById("logout-button").addEventListener("click", confirmLogout);
function confirmLogout() {
  if (confirm("Are you sure you want to logout?")) {
    logoutAdmin();
  }
}
function logoutAdmin() {
  window.location.href = 'index.html';
}
// ======================================================================================
// Function to update the dropdown button text with the admin name

  const dropdownButton = document.getElementById("admin-dropdown");
  // dropdownButton.textContent = "Welcome, " + userName;



// ===================================================================================
// Implemetations
const apiEndpoint = 'http://localhost:8088';

// Function to add a new admin
function addAdmin(event) {
  event.preventDefault();

  // Get input values from the form fields
  const name = document.getElementById("name").value;
  const address = document.getElementById("address").value;
  const email = document.getElementById("email").value;
  const mobile = document.getElementById("mobile").value;
  const username = document.getElementById("customer-username").value;
  const password = document.getElementById("customer-password").value;

  document.getElementById("inputform").reset();
 
  var myHeaders = {
    'Authorization': 'Bearer ' + jwtToken,
    'Content-Type': 'application/json',
}
  // Create a data object with the input values
  const raw = {
    "name": name,
    "userName": username,
    "password": password,
    "address": address,
    "mobileNumber": mobile,
    "email": email
  };

 console.log(raw)
var requestOptions = {
  method: 'POST',
  headers: myHeaders,
  body: JSON.stringify(raw),
  redirect: 'follow'
};

fetch(apiUrl+"add", requestOptions)
    .then(response => response.text())
    .then(result => {
      console.log(result);
      // Show success message
      alert("Signup successful, Please Sign In");
     
    })
    .catch(error => console.log('error', error));
}

// Function to update an admin
function updateAdmin() {
  const name = document.getElementById('adminName').value;
  const userName = document.getElementById('adminUserName').value;
  const password = document.getElementById('adminPassword').value;
  const address = document.getElementById('adminAddress').value;
  const mobileNumber = document.getElementById('adminMobileNumber').value;
  const email = document.getElementById('adminEmail').value;

  // Create an object with the updated admin data
  const updatedAdmin = {
    name,
    userName,
    password,
    address,
    mobileNumber,
    email,
  };

  // Make a PUT request to update the admin details
  fetch(`${apiEndpoint}/admin/{adminId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(updatedAdmin),
  })
    .then(response => {
      // Handle the response
      if (response.ok) {
        console.log('Admin details updated successfully.');

        // Fetch the updated admin data
        return fetch(`${apiEndpoint}/admin`);
      } else {
        // Handle errors
        throw new Error('Failed to update admin details.');
      }
    })
    .then(response => response.json())
    .then(updatedAdminData => {
      // Display the updated admin data in a table format
      displayAdminData(updatedAdminData);
    })
    .catch(error => {
      console.error('An error occurred while updating admin details:', error);
    });
}

// Function to display admin data in a table format
function displayAdminData(adminData) {
  const content = document.querySelector('.content');

  // Clear previous content
  content.innerHTML = '';

  // Create the table element and apply borders
  const table = document.createElement('table');
  table.classList.add('admin-table');
  table.style.borderCollapse = 'collapse';
  table.style.border = '1px solid black';

  // Create the table header
  const thead = document.createElement('thead');
  const headerRow = document.createElement('tr');
  headerRow.innerHTML = `
    <th>Name</th>
    <th>Username</th>
    <th>Password</th>
    <th>Address</th>
    <th>Mobile Number</th>
    <th>Email</th>
  `;
  thead.appendChild(headerRow);
  table.appendChild(thead);

  // Create the table body
  const tbody = document.createElement('tbody');

  // Create a row for the updated admin data
  const row = document.createElement('tr');
  row.innerHTML = `
    <td>${adminData.name}</td>
    <td>${adminData.userName}</td>
    <td>${adminData.password}</td>
    <td>${adminData.address}</td>
    <td>${adminData.mobileNumber}</td>
    <td>${adminData.email}</td>
  `;
  tbody.appendChild(row);

  // Append the table body to the table
  table.appendChild(tbody);

  // Add border to the table cells
  const cells = table.querySelectorAll('td, th');
  cells.forEach(cell => {
    cell.style.border = '1px solid black';
    cell.style.padding = '8px';
  });

  // Append the table to the content section
  content.appendChild(table);
}


// Function to delete an admin
function deleteAdmin() {
  const adminId = document.getElementById('adminId').value;

  // Perform validation or additional checks if needed

  // Make a DELETE request to delete the admin
  fetch(`${apiEndpoint}/admins/${adminId}`, {
    method: 'DELETE'
  })
    .then(response => {
      // Handle the response
      if (response.ok) {
        // Display success message
        console.log('Admin deleted successfully!');
      } else {
        // Handle errors
        console.error('Failed to delete admin.');
      }
    })
    .catch(error => {
      console.error('An error occurred while deleting admin:', error);
    });
}

// Function to get all trips of a customer
function getAllTripsOfCustomer() {
  const customerIdInput = document.getElementById('customerIdInput');
  const customerId = customerIdInput.value;

  // Make a GET request to retrieve all trips of a customer
  fetch(`${apiEndpoint}/customer/allTrips/${customerId}`)
    .then(response => response.json())
    .then(data => {
      // Display the trips
      const tripsContainer = document.getElementById('tripsContainer');
      tripsContainer.innerHTML = `<h3>All Trips of Customer ${customerId}:</h3>`;

      if (data.length === 0) {
        tripsContainer.innerHTML += '<p>No trips found.</p>';
      } else {
        const table = document.createElement('table');
        table.innerHTML = `
          <tr>
            <th>Trip ID</th>
            <th>Customer Name</th>
            <th>Cab ID</th>
            <th>From Location</th>
            <th>To Location</th>
            <th>Status</th>
          </tr>
        `;

        data.forEach(trip => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${trip.tripId}</td>
            <td>${trip.customer.name}</td>
            <td>${trip.cab.cabId}</td>
            <td>${trip.fromLocation}</td>
            <td>${trip.toLocation}</td>
            <td>${trip.status ? 'Completed' : 'Pending'}</td>
          `;
          table.appendChild(row);
        });

        tripsContainer.appendChild(table);
      }
    })
    .catch(error => {
      console.error(`An error occurred while fetching all trips of customer ${customerId}:`, error);
    });
}

// Function to get trips cab wise
function getTripsCabWise() {
  const cabIdInput = document.getElementById('cabIdInput');
  const cabId = cabIdInput.value;

  // Make a GET request to retrieve trips cab wise
  fetch(`${apiEndpoint}/cab/${cabId}`)
    .then(response => response.json())
    .then(data => {
      // Display the trips cab wise
      const tripsContainer = document.getElementById('tripsContainer');
      tripsContainer.innerHTML = `<h3>Trips for Cab ${cabId}:</h3>`;

      if (data.length === 0) {
        tripsContainer.innerHTML += '<p>No trips found.</p>';
      } else {
        const table = document.createElement('table');
        table.innerHTML = `
          <tr>
            <th>Trip ID</th>
            <th>Customer Name</th>
            <th>Cab ID</th>
            <th>From Location</th>
            <th>To Location</th>
            <th>Status</th>
          </tr>
        `;

        data.forEach(trip => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${trip.tripId}</td>
            <td>${trip.customer.name}</td>
            <td>${trip.cab.cabId}</td>
            <td>${trip.fromLocation}</td>
            <td>${trip.toLocation}</td>
            <td>${trip.status ? 'Completed' : 'Pending'}</td>
          `;
          table.appendChild(row);
        });

        tripsContainer.appendChild(table);
      }
    })
    .catch(error => {
      console.error(`An error occurred while fetching trips for Cab ${cabId}:`, error);
    });
}

// Function to get trips customer wise
function getTripsCustomerWise() {
  const customerIdInput = document.getElementById('customerIdInput');
  const customerId = customerIdInput.value;

  // Make a GET request to retrieve trips customer wise
  fetch(`${apiEndpoint}/customer/${customerId}`)
    .then(response => response.json())
    .then(data => {
      // Display the trips customer wise
      const tripsContainer = document.getElementById('tripsContainer');
      tripsContainer.innerHTML = `<h3>Trips for Customer ${customerId}:</h3>`;

      if (data.length === 0) {
        tripsContainer.innerHTML += '<p>No trips found.</p>';
      } else {
        const table = document.createElement('table');
        table.innerHTML = `
          <tr>
            <th>Trip ID</th>
            <th>Customer Name</th>
            <th>Cab ID</th>
            <th>From Location</th>
            <th>To Location</th>
            <th>Status</th>
          </tr>
        `;

        data.forEach(trip => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${trip.tripId}</td>
            <td>${trip.customer.name}</td>
            <td>${trip.cab.cabId}</td>
            <td>${trip.fromLocation}</td>
            <td>${trip.toLocation}</td>
            <td>${trip.status ? 'Completed' : 'Pending'}</td>
          `;
          table.appendChild(row);
        });

        tripsContainer.appendChild(table);
      }
    })
    .catch(error => {
      console.error(`An error occurred while fetching trips customer wise:`, error);
    });
}

//Function for Display Customer Querys
async function fetchAndDisplayData() {
  try {
    const headers = {
      'Authorization': 'Bearer ' + jwtToken,
      'Content-Type': 'application/json',
  };
    const response = await fetch('http://localhost:8088/admins/querys', {
      headers: headers,
  });
    if (!response.ok) {
      throw new Error('Failed to fetch data');
    }
    const data = await response.json();
    const tableBody = document.querySelector('#customerTable tbody');

    // Iterate over the data and create table rows
    data.forEach(customer => {
      const row = document.createElement('tr');
      row.innerHTML = `
              <td>${customer.queryId}</td>
              <td>${customer.firstName + " " + customer.lastName}</td>
              <td>${customer.state}</td>
              <td>${customer.message}</td>
              <td>${customer.open}</td>
          `;
      tableBody.appendChild(row);
    });
  } catch (error) {
    console.error('Error fetching data:', error);
  }
}
//Function for redirect to home page
function logoutCustomer() {
  window.location.href = 'index.html';
}