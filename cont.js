document.getElementById("form").addEventListener("submit", (event)=>{
    handleQuery(event)
    setTimeout(()=>{
        handleShow(event)
    },2000)
});

function handleQuery(event) {
    event.preventDefault();

    const userQuery = document.getElementById("inp").value;

    const apiUrl = "http://localhost:8080/dowithai"; // Change port if needed

    fetch(apiUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ query: userQuery }) // Send JSON with "query" key
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log("Response from server:", data); // Log API response
    })
    .catch(error => {
        console.error("Fetch error:", error);
    });
}



function handleShow(event) {
    event.preventDefault();

    fetch("http://localhost:8080/all")
        .then(response => response.json())
        .then(data => {
            const out = document.getElementById("out");
            out.innerHTML = ""; // Clear previous content

            if (data.length === 0) {
                out.innerHTML = "<p>No users found.</p>";
                return;
            }

            // Create a list to display users
            const list = document.createElement("ul");

            data.forEach(user => {
                const listItem = document.createElement("li");
                listItem.textContent = `ID: ${user.id} - Name: ${user.name} - Email: ${user.email}`;
                list.appendChild(listItem);
            });

            out.appendChild(list);
        })
        .catch(err => {
            console.error(err);
            document.getElementById("out").innerHTML = "<p style='color: red;'>Error fetching users.</p>";
        });
}

