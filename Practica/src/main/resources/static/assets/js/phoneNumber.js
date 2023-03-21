var btn = document.getElementById("customer-service-btn");
var phone = document.getElementById("customer-service-phone");

btn.addEventListener("click", function() {
    if (phone.style.display === "none") {
        phone.style.display = "block";
    } else {
        phone.style.display = "none";
    }
});