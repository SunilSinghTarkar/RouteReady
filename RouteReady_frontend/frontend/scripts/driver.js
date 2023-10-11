function loginDriver() {
  window.location.href = 'driverDashboard.html';
}


// function loginDriver() {
//   const username = document.getElementById('username').value;
//   const password = document.getElementById('password').value;

//   // Make API call to authenticate the admin
//   fetch('/api/login', {
//     method: 'POST',
//     headers: {
//       'Content-Type': 'application/json'
//     },
//     body: JSON.stringify({ username, password })
//   })
//   .then(response => {
//     if (response.ok) {
//       // Authentication successful, redirect to admin dashboard
//       window.location.href = 'driverDashboard.html';
//     } else {
//       // Authentication failed, display error message
//       document.getElementById('error-message').textContent = 'Invalid username or password';
//     }
//   })
//   .catch(error => {
//     // Handle any errors that occurred during the API call
//     console.error('Error:', error);
//   });
// }


function signupDriver(event) {
  event.preventDefault();

  // Get input values from the form fields
  const name = document.getElementById("name").value;
  const address = document.getElementById("address").value;
  const email = document.getElementById("email").value;
  const mobile = document.getElementById("mobile").value;
  const username = document.getElementById("driver-username").value;
  const passwordInput = document.getElementById("driver-password").value;
  const licenceNum = document.getElementById("license").value;


  var myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");

  // Create a data object with the input values
  const raw = {
    "name": name,
    "userName": username,
    "password": passwordInput,
    "address": address,
    "mobileNumber": mobile,
    "email": email,
    "isDeleted": false,
    "licenceNumber": licenceNum
  };
  // const raw={
  //   "name": "Ramesh Singh",
  //   "userName": "ramesh444",
  //   "password": "securePass",
  //   "address": "678, MNO Road, Jaipur",
  //   "mobileNumber": "7654301098",
  //   "email": "rameshsingh@example.com",
  //   "role": "driver",
  //   "licenceNumber": "DL87"
  // }

  console.log(raw);


  var requestOptions = {
    method: 'POST',
    headers: myHeaders,
    body: JSON.stringify(raw),
    redirect: 'follow'
  };

  fetch("http://localhost:8088/drivers/add", requestOptions)
    .then(response => response.text())
    .then(result => {
      console.log(result);
      // Show success message
      alert("Signup successful, Please Sign In");
      // Redirect to another page or perform any other action
      // window.location.href = 'customerDashboard.html';
    })
    .catch(error => console.log('error', error));
}