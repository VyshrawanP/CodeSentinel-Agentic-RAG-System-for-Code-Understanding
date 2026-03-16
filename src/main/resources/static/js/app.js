// app.js
const chat = document.getElementById("chat");
const questionInput = document.getElementById("questionInput");
const fileUpload = document.getElementById("fileUpload");
const fileText = document.querySelector(".file-text");

// File upload visual feedback
fileUpload.addEventListener("change", function(e) {
    const file = e.target.files[0];
    if (file) {
        fileText.textContent = file.name;
        fileText.style.color = "var(--accent-primary)";
        document.querySelector(".file-dropzone").style.borderColor = "var(--accent-primary)";
        document.querySelector(".file-dropzone").style.background = "rgba(99, 102, 241, 0.1)";
    }
});

// Drag and drop support
const dropzone = document.querySelector(".file-dropzone");

["dragenter", "dragover", "dragleave", "drop"].forEach(eventName => {
    dropzone.addEventListener(eventName, preventDefaults, false);
});

function preventDefaults(e) {
    e.preventDefault();
    e.stopPropagation();
}

["dragenter", "dragover"].forEach(eventName => {
    dropzone.addEventListener(eventName, highlight, false);
});

["dragleave", "drop"].forEach(eventName => {
    dropzone.addEventListener(eventName, unhighlight, false);
});

function highlight(e) {
    dropzone.style.borderColor = "var(--accent-primary)";
    dropzone.style.background = "rgba(99, 102, 241, 0.2)";
    dropzone.style.transform = "scale(1.02)";
}

function unhighlight(e) {
    dropzone.style.borderColor = "";
    dropzone.style.background = "";
    dropzone.style.transform = "";
}

dropzone.addEventListener("drop", handleDrop, false);

function handleDrop(e) {
    const dt = e.dataTransfer;
    const files = dt.files;
    fileUpload.files = files;
    
    if (files[0]) {
        fileText.textContent = files[0].name;
        fileText.style.color = "var(--accent-primary)";
    }
}

function addMessage(text, type) {
    // Remove welcome message if it exists
    const welcome = document.querySelector(".welcome-message");
    if (welcome) welcome.remove();

    const msg = document.createElement("div");
    msg.className = "message " + type;

    const bubble = document.createElement("div");
    bubble.className = "bubble";
    
    // Parse markdown-like code blocks for AI messages
    if (type === "ai") {
        bubble.innerHTML = formatMessage(text);
    } else {
        bubble.innerText = text;
    }

    msg.appendChild(bubble);
    chat.appendChild(msg);
    chat.scrollTop = chat.scrollHeight;
}

function formatMessage(text) {
    // Simple formatting for code blocks
    return text
        .replace(/```([\s\S]*?)```/g, '<pre><code>$1</code></pre>')
        .replace(/`([^`]+)`/g, '<code>$1</code>')
        .replace(/\n/g, '<br>');
}

function showTypingIndicator() {
    const msg = document.createElement("div");
    msg.className = "message ai typing";
    msg.id = "typing-indicator";
    
    const bubble = document.createElement("div");
    bubble.className = "bubble typing-indicator";
    bubble.innerHTML = "<span></span><span></span><span></span>";
    
    msg.appendChild(bubble);
    chat.appendChild(msg);
    chat.scrollTop = chat.scrollHeight;
}

function removeTypingIndicator() {
    const typing = document.getElementById("typing-indicator");
    if (typing) typing.remove();
}

async function uploadFile() {
    const file = document.getElementById("fileUpload").files[0];
    if (!file) {
        showNotification("Please select a file first", "error");
        return;
    }

    const formData = new FormData();
    formData.append("file", file);

    // Visual feedback
    const btn = document.querySelector(".upload-btn");
    const originalText = btn.innerHTML;
    btn.innerHTML = `<span>Uploading...</span><svg class="btn-icon spinning" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 2v4m0 12v4m-4-8H4m16 0h-4m2.5-5.5L17 12l2.5 2.5M6.5 6.5L9 9 6.5 11.5"/></svg>`;
    btn.disabled = true;

    try {
        await fetch("/api/code/upload", {
            method: "POST",
            body: formData
        });
        
        showNotification("File uploaded successfully!", "success");
        addMessage(`Uploaded: ${file.name}`, "user");
        addMessage("I've received your file. What would you like to know about it?", "ai");
        
        // Reset file input
        fileUpload.value = "";
        fileText.textContent = "Choose file or drop here";
        fileText.style.color = "";
        document.querySelector(".file-dropzone").style.borderColor = "";
        document.querySelector(".file-dropzone").style.background = "";
        
    } catch (error) {
        showNotification("Upload failed. Please try again.", "error");
    } finally {
        btn.innerHTML = originalText;
        btn.disabled = false;
    }
}

async function askQuestion() {
    const input = document.getElementById("questionInput");
    const question = input.value.trim();
    
    if (!question) return;

    addMessage(question, "user");
    input.value = "";
    
    showTypingIndicator();

    try {
        const response = await fetch("/api/code/ask", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                question: question
            })
        });

        const data = await response.text();
        removeTypingIndicator();
        addMessage(data, "ai");
        
    } catch (error) {
        removeTypingIndicator();
        addMessage("Sorry, I encountered an error processing your request. Please try again.", "ai");
    }
}

// Enter key support
questionInput.addEventListener("keypress", function(e) {
    if (e.key === "Enter" && !e.shiftKey) {
        e.preventDefault();
        askQuestion();
    }
});

// Notification system
function showNotification(message, type) {
    const notification = document.createElement("div");
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 16px 24px;
        background: ${type === "success" ? "rgba(16, 185, 129, 0.9)" : "rgba(239, 68, 68, 0.9)"};
        color: white;
        border-radius: 12px;
        font-weight: 500;
        z-index: 1000;
        animation: slideInRight 0.3s ease;
        backdrop-filter: blur(10px);
        border: 1px solid rgba(255,255,255,0.1);
    `;
    notification.textContent = message;
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.style.animation = "slideOutRight 0.3s ease";
        setTimeout(() => notification.remove(), 300);
    }, 3000);
}

// Add animation styles
const style = document.createElement("style");
style.textContent = `
    @keyframes slideInRight {
        from { transform: translateX(100%); opacity: 0; }
        to { transform: translateX(0); opacity: 1; }
    }
    @keyframes slideOutRight {
        from { transform: translateX(0); opacity: 1; }
        to { transform: translateX(100%); opacity: 0; }
    }
    .spinning {
        animation: spin 1s linear infinite;
    }
    @keyframes spin {
        from { transform: rotate(0deg); }
        to { transform: rotate(360deg); }
    }
`;
document.head.appendChild(style);

// Clear chat functionality
document.querySelector(".icon-btn").addEventListener("click", function() {
    chat.innerHTML = `
        <div class="welcome-message">
            <div class="welcome-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                    <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5"/>
                </svg>
            </div>
            <h3>Welcome to CodeSentinel</h3>
            <p>Upload your code and ask questions about it. I'll analyze, explain, and help you understand every line.</p>
        </div>
    `;
});