const jwtToken = localStorage.getItem("jwtToken");
// API endpoint URL
const apiUrl = 'http://localhost:8088/customers/';
const showLoginFormButton = document.getElementById("showLoginForm");
const closeLoginFormButton = document.getElementById("closeLoginForm");
const loginModal = document.getElementById("loginModal");

showLoginFormButton.addEventListener("click", function () {
    // Show the login modal
    //if the user is already logged in then refresh the page
    if(jwtToken){
       if(confirm("Are you sure You want to logout?")){
        localStorage.clear();

       }

      window.location.reload();

    } 
      
    else loginModal.classList.add("show");
});

closeLoginFormButton.addEventListener("click", function () {
    // Hide the login modal
    loginModal.classList.remove("show");
});

console.log("inside script.js file")
// Function for user login 
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
    if(response.status===401){
      document.getElementById("serverresponse").innerText="Invalid username or password";
    }
    
    if (response.status === 202) {
      // Retrieve the JWT token from the response headers
      const token = response.headers.get('Authorization');
      const data = await response.json();
     console.log(data)
     
      if (token) {
        console.log('JWT Token:', token);
        localStorage.setItem("jwtToken", token);
        localStorage.setItem("userId", data.userId);
        localStorage.setItem("userName", data.userName.split(" ")[0]);
        localStorage.setItem("userRole", data.userRole);
        if(data.userRole=="ADMIN")window.location.href="./adminDashboard.html"
        else if(data.userRole=="DRIVER") window.location.href="./driverDashboard.html"
        else window.location.href="./customerDashboard.html"
        
      } else {
        throw new Error('JWT Token not found in headers');
      }
    }

     else {
      throw new Error('Login failed');
    }
  } catch (error) {
    console.error('Error:', error); // Handle errors
  }
}