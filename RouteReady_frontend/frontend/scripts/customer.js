const userName = localStorage.getItem("userName");
if(userName)window.location.href="./customerDashboard.html"
// API endpoint URL
const apiUrl = 'http://localhost:8088/customers/';
// // Add event listener to the form submit event
//Function for Create new Customer
function signupCustomer(event) {
  event.preventDefault();

  // Get input values from the form fields
  const name = document.getElementById("name").value;
  const address = document.getElementById("address").value;
  const email = document.getElementById("email").value;
  const mobile = document.getElementById("mobile").value;
  const username = document.getElementById("customer-username").value;
  const password = document.getElementById("customer-password").value;

  
  var myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");
  
  // Create a data object with the input values
  const raw = {
    "name": name,
    "userName": username,
    "password": password,
    "address": address,
    "mobileNumber": mobile,
    "email": email
  };


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

//Function for User Login
async function handleLogin(event) {
  event.preventDefault(); // Prevent the default form submission

  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  const base64Credentials = btoa(username + ':' + password);
  const headers = new Headers({
      'Authorization': 'Basic ' + base64Credentials
    });
    const requestOptions = {
      method: 'GET',
      headers: headers,
    };
  console.log(username,password)
try {
  const response = await fetch(`${apiUrl}signIn`, requestOptions);

  if (response.status === 202) {
    // Retrieve the JWT token from the response headers
    const token = response.headers.get('Authorization');
    const data = await response.json();
   console.log(data)
   
    if (token) {
      console.log('JWT Token:', token);
      localStorage.setItem("jwtToken", token);
      localStorage.setItem("userId", data.userId);
      localStorage.setItem("userName", data.userName);
      localStorage.setItem("userRole", data.userRole);
      if(data.userRole=="DRIVER")
      window.location.href="./driverDashboard.html"
    else 
    window.location.href="./customerDashboard.html"
      
    } else {
      throw new Error('JWT Token not found in headers');
    }
  } else {
    throw new Error('Login failed');
  }
} catch (error) {
  console.error('Error:', error); // Handle errors
}
}