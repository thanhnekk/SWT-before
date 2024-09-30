const express = require('express');
const bodyParser = require('body-parser');
const { exec } = require('child_process');

const app = express();
const port = 3000;

app.use(bodyParser.text());

app.post('/runcode', (req, res) => {
    const code = req.body;

    // Run code securely (replace with your own security measures)
    exec(code, (error, stdout, stderr) => {
        if (error) {
            console.error(`exec error: ${error}`);
            res.status(500).send('Error executing code');
            return;
        }

        console.log(`stdout: ${stdout}`);
        console.error(`stderr: ${stderr}`);

        res.send(stdout);
    });
});

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
const form = document.getElementById('codeForm');
const codeInput = document.getElementById('code');
const outputDiv = document.getElementById('output');

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const code = codeInput.value.trim();

    if (!code) {
        alert('Please enter some code.');
        return;
    }

    try {
        const response = await fetch('/runcode', {
            method: 'POST',
            headers: {
                'Content-Type': 'text/plain'
            },
            body: code
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const output = await response.text();
        outputDiv.textContent = output;
    } catch (error) {
        console.error('Error:', error);
        outputDiv.textContent = 'Error executing code.';
    }
});
