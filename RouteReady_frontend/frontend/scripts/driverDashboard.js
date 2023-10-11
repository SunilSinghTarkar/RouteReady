const apiUrl='http://localhost:8088/';
const userId = localStorage.getItem('userId'); // Retrieve customer ID from localStorage 
const userName = localStorage.getItem("userName");
const jwtToken = localStorage.getItem("jwtToken");
console.log(userName, userId, jwtToken);


// Code for display User Name
document.getElementById("userName").textContent = userName;

// Function to display the selected content
let homepage = document.getElementById("nav-logo-img");
homepage.addEventListener("click",()=>{
    logoutCustomer();
}
);
function displayContent(option) {
    const content = document.querySelector('.content');

    // Clear previous content
    content.innerHTML = '';

    // Render the selected content
    switch (option) {
      case 'update-details':
        content.innerHTML = `
        <h2 style="text-align: center">Update Driver</h2>
        <form id="inputform" style="width: 25%; gap: 10px; margin: 10px auto; display: flex; flex-direction: column; padding: 20px; border: 2px solid #ccc; background-color: #f9f9f9;"     onsubmit="updateDriver(event)" >
            <label for="customerName">Driver Name:</label>
            <input type="text" id="driverName" name="customerName">
             <label for="mobile">Mobile Number:</label>
        <input type="text" id="driverMobile" name="mobile" required>
            <label for="customerEmail">Email:</label>
            <input type="email" id="driverEmail" name="customerEmail">
            <label for="address">Address:</label>
           <input type="text" id="driverAddress" name="address" required>
            <button type="submit" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Update</button>
        </form>
    `;
    break;
        case 'insert-cab':
            content.innerHTML = `
          <h2 style="text-align: center;">Insert Cab</h2>
          <form style="width: 30%; gap: 10px; margin: 10px auto; display: flex; flex-direction: column;">
            <label for="cabId">Cab ID</label>
            <input type="text" id="cabId" name="cabId">
            <label for="cabType">Cab Type</label>
            <input type="text" id="cabType" name="cabType">
            <button type="submit" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Insert Cab</button>
          </form>
        `;
            break;
        case 'delete-cab':
            content.innerHTML = `
          <h2 style="text-align: center;">Delete Cab</h2>
          <form style="width: 30%; gap: 10px; margin: 10px auto; display: flex; flex-direction: column;">
            <label for="cabId">Cab ID</label>
            <input type="text" id="cabId" name="cabId">
            <button type="submit" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Delete Cab</button>
          </form>
        `;
            break;
        case 'view-cab':
            content.innerHTML = '<h2 style="text-align: center;">View Cab</h2>';
            viewCab(); // Invoke the viewCab() function
            break;
        case 'view-cab-by-type':
            content.innerHTML = `
          <h2 style="text-align: center;">View Cab by Type</h2>
          <div style="width: 30%; gap: 10px; margin: 10px auto; display: flex; flex-direction: column;">
            <label for="cabType">Enter Cab Type:</label>
            <input type="text" id="cabType" />
            <button onclick="viewCabByType()" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">View Cabs</button>
          </div>
          <div id="cabTable"></div>
        `;
            break;
        case 'count-cab-by-type':
            content.innerHTML = `
          <h2 style="text-align: center;">Count Cab by Type</h2>
          <div style="width: 30%; gap: 10px; margin: 10px auto; display: flex; flex-direction: column;">
            <label for="cabType">Enter Cab Type:</label>
            <input type="text" id="cabType" />
            <button onclick="countCabByType()" style="font-size: 16px; background-color: #007bff; color: #fff; padding: 5px 10px; border: none; border-radius: 3px; cursor: pointer;">Count Cabs</button>
          </div>
          <div id="countResult"></div>
        `;
            break;
        default:
            content.innerHTML = '<h2 style="text-align: center;">Welcome, Driver!</h2><p>Select an option from the sidebar to view the results.</p>';
    }
}
// ************************************************************************************************************************
//Code for User Trip history
const table = document.getElementById('tripTable');
const tripApi = 'http://localhost:8088/tripbookings/driver/'
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
        if(data.length==0){
          console.log("Hisotry not found")
          document.getElementById("tripDetailsContainer").innerHTML="";
        }
        data.forEach(entry => {
            const row = document.createElement('tr');

            const keys = [
                'tripBookingId', 'fromLocation', 'toLocation', 'fromDateTime', 'toDateTime',
                'distanceInKm', 'driver.cab.perKmRate', 'bill', 'customer.name', 'customer.mobileNumber', 'customer.email'
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

// Function to insert a cab
// function insertCab(event) {
//   event.preventDefault();

//   const carType = document.getElementById('carType').value;
//   const perKmRate = parseFloat(document.getElementById('perKmRate').value);

//   // Make a POST request to insert the cab
//   fetch(`${apiEndpoint}/cabs/add`, {
//     method: 'POST',
//     headers: {
//       'Content-Type': 'application/json'
//     },
//     body: JSON.stringify({
//       carType: carType,
//       perKmRate: perKmRate
//     })
//   })
//     .then(response => {
//       // Handle the response
//       if (response.ok) {
//         // Display success message
//         console.log('Cab inserted successfully!');
//       } else {
//         // Handle errors
//         console.error('Failed to insert cab.');
//       }
//     })
//     .catch(error => {
//       console.error('An error occurred while inserting cab:', error);
//     });
// }


// Function to delete a cab
// function deleteCab(event) {
//   event.preventDefault();

//   const cabId = document.getElementById('cabId').value;

//   // Make a DELETE request to delete the cab
//   fetch(`${apiEndpoint}/cabs/${cabId}`, {
//     method: 'DELETE'
//   })
//     .then(response => {
//       // Handle the response
//       if (response.ok) {
//         // Display success message
//         console.log('Cab deleted successfully!');
//       } else {
//         // Handle errors
//         console.error('Failed to delete cab.');
//       }
//     })
//     .catch(error => {
//       console.error('An error occurred while deleting cab:', error);
//     });
// }

// Function to view all cabs
// function viewCab() {
//   // Make a GET request to fetch all cabs
//   fetch(`${apiEndpoint}/cabs`)
//     .then(response => response.json())
//     .then(data => {
//       // Display the cab data
//       const cabTable = document.getElementById('cabTable');
//       cabTable.innerHTML = '<h3>Available Cabs:</h3>';
      
//       if (data.length === 0) {
//         cabTable.innerHTML += '<p>No cabs found.</p>';
//       } else {
//         const table = document.createElement('table');
//         table.innerHTML = `
//           <tr>
//             <th>Cab ID</th>
//             <th>Car Type</th>
//             <th>Per Kilometer Rate</th>
//           </tr>
//         `;
  
//         data.forEach(cab => {
//           const row = document.createElement('tr');
//           row.innerHTML = `
//             <td>${cab.cabId}</td>
//             <td>${cab.carType}</td>
//             <td>${cab.perKmRate}</td>
//           `;
//           table.appendChild(row);
//         });
  
//         cabTable.appendChild(table);
//       }
//     })
//     .catch(error => {
//       console.error('An error occurred while fetching cabs:', error);
//     });
// }

// // Function to view cabs by type
// function viewCabByType() {
//   const cabType = document.getElementById('cabType').value;

//   // Make a GET request to fetch cabs by type
//   fetch(`${apiEndpoint}/cabs?type=${cabType}`)
//     .then(response => response.json())
//     .then(data => {
//       // Display the cab data
//       const cabTable = document.getElementById('cabTable');
//       cabTable.innerHTML = '<h3>Available Cabs:</h3>';

//       if (data.length === 0) {
//         cabTable.innerHTML += '<p>No cabs found for the given type.</p>';
//       } else {
//         const table = document.createElement('table');
//         table.innerHTML = `
//           <tr>
//             <th>Cab ID</th>
//             <th>Car Type</th>
//             <th>Per Kilometer Rate</th>
//           </tr>
//         `;

//         data.forEach(cab => {
//           const row = document.createElement('tr');
//           row.innerHTML = `
//             <td>${cab.cabId}</td>
//             <td>${cab.carType}</td>
//             <td>${cab.perKmRate}</td>
//           `;
//           table.appendChild(row);
//         });

//         cabTable.appendChild(table);
//       }
//     })
//     .catch(error => {
//       console.error('An error occurred while fetching cabs by type:', error);
//     });
// }

// // Function to count cabs by type
// function countCabByType() {
//   const cabType = document.getElementById('cabType').value;

//   // Make a GET request to count cabs by type
//   fetch(`${apiEndpoint}/cabs/count?type=${cabType}`)
//     .then(response => response.json())
//     .then(data => {
//       // Display the count result
//       const countResult = document.getElementById('countResult');
//       countResult.innerHTML = '<h3>Cab Count:</h3>';

//       if (data.count === 0) {
//         countResult.innerHTML += '<p>No cabs found for the given type.</p>';
//       } else {
//         countResult.innerHTML += `<p>Count of cabs for type '${cabType}': ${data.count}</p>`;
//       }
//     })
//     .catch(error => {
//       console.error('An error occurred while counting cabs by type:', error);
//     });
// }

document.getElementById("logout-button").addEventListener("click", confirmLogout);

function confirmLogout() {
    if (confirm("Are you sure you want to logout?")) {
        logoutCustomer();
    }
}
function logoutCustomer() {
    window.location.href = 'index.html';
}
 ///Function for Delete Driver Account
async function deleteDriver(){
   if( confirm("Are you sure you want to Delete Account?")){
      
    const headers = {
      'Authorization': `Bearer ${jwtToken}`,
      'Content-Type': 'application/json', // Set the content type as needed
    };
  
    const deleteRequest = {
      method: 'DELETE',
      headers: headers,
    };
  
    try {
      const response = await fetch(`${apiUrl}drivers/delete/${userId}`, deleteRequest);
      
      if (response.ok) {
        console.log('Delete request was successful.');
        alert("Account Deleted Successfully")
        localStorage.clear();
        window.location.href="./index.html";
      } else {
        console.error('Delete request failed:', response.statusText);
      }
    } catch (error) {
      console.error('Error:', error);
    }

   }
   
}

async function updateDriver(event) {
  event.preventDefault();
  const name = document.getElementById("driverName").value;
  const email = document.getElementById("driverEmail").value;
  const mobile = document.getElementById("driverMobile").value;
  const address = document.getElementById("driverAddress").value;
  const driverData = {
      "name": name,
      "email": email,
      "address": address,
      "mobileNumber": mobile
  };
  console.log(driverData)

  try {
      const response = await fetch(`${apiUrl}drivers/update/${userId}`, {
          method: 'PATCH',
          headers: {
              'Authorization': 'Bearer ' + jwtToken,
              'Content-Type': 'application/json',
          },
          body: JSON.stringify(driverData),
      });

      if (!response.ok) {
          throw new Error('Failed to update Driver');
      }

      const updateDriver = await response.json();
      document.getElementById("inputform").reset();
      console.log(updateDriver)
      return updateDriver;
  } catch (error) {

      console.error('Error updating Driver:', error);
      throw error;
  }

}
