const chat = document.getElementById("chat");

function addMessage(text, type) {

    const msg = document.createElement("div");
    msg.className = "message " + type;

    const bubble = document.createElement("div");
    bubble.className = "bubble";
    bubble.innerText = text;

    msg.appendChild(bubble);
    chat.appendChild(msg);

    chat.scrollTop = chat.scrollHeight;
}

async function uploadFile(){

    const file = document.getElementById("fileUpload").files[0];

    const formData = new FormData();
    formData.append("file", file);

    await fetch("/api/code/upload", {
        method: "POST",
        body: formData
    });

    alert("File uploaded!");
}

async function askQuestion(){

    const input = document.getElementById("questionInput");
    const question = input.value;

    addMessage(question, "user");

    input.value = "";

    const response = await fetch("/api/code/ask", {
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify({
            question:question
        })
    });

    const data = await response.text();

    addMessage(data, "ai");
}