document.addEventListener("DOMContentLoaded", function() {
    document.querySelector("form").addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent the default form submission

        const form = event.target;
        const formData = new FormData(form);

        fetch('submit', {
            method: 'POST',
            body: new URLSearchParams(formData)
        })
            .then(response => response.text())
            .then(result => {
                document.getElementById('result').innerText = result;
            })
            .catch(error => {
                document.getElementById('result').innerText = 'Error: ' + error;
            });
    });
});
