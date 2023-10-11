const userbutton=document.getElementById("showLoginForm");
const userrole=localStorage.getItem("userRole");
console.log("userrole "+userrole)
const username=localStorage.getItem("userName");
if(username){
  userbutton.innerText="Hello "+username;
  
}
if(userrole=="ADMIN"){
 const hideadminpage= document.getElementById("hideadminpage");
 hideadminpage.style.display="block";
}





document.getElementById("queryForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent the default form submission
  
    const firstName = document.getElementById("fname").value;
    const lastName = document.getElementById("lname").value;
    const state = document.getElementById("country").value;
    const message = document.getElementById("subject").value;
  
    const formData = {
      firstName: firstName,
      lastName: lastName,
      state: state,
      message: message
    };
   console.log(formData)
    // Convert the form data to a JSON string
    const jsonData = JSON.stringify(formData);
    
    // Make a POST request using fetch
    fetch('http://localhost:8088/admin/querys', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: jsonData
    })
    .then(response => response.json())
    .then(data => {
      // Handle the response from the server
      console.log('Server response:', data);
       // Reset the form after successful submission
       document.getElementById("queryForm").reset();
    })
    .catch(error => {
      console.error('Error:', error);
    });

  });

