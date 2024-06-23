function showPasswordModal() {
  document.getElementById("passwordModal").style.display = "block";
  document.querySelector(".profile").classList.add("overlay"); // Thêm lớp overlay vào phần tử profile
}

function hidePasswordModal() {
  document.getElementById("passwordModal").style.display = "none";
  document.querySelector(".profile").classList.remove("overlay"); // Loại bỏ lớp overlay từ phần tử profile
}
document.getElementById("saving-profile").addEventListener("submit", function(event) {
      event.preventDefault();
      var form = event.target;
      var formData = new FormData(form);

      fetch(form.action, {
          method: form.method,
          body: formData,
      }).then(response => {
          if (response.ok) {
             window.location.href = "/cashier-profile"
          } else {
              alert("An error occurred while updating the profile.");
          }
      }).catch(error => {
          alert("An error occurred: " + error.message);
      });
  });