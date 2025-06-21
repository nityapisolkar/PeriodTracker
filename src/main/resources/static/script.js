let addCountIndex = 0;
function addFnIndex() {
    if (addCountIndex === 0) {
        const inputFields = document.getElementById("inputFields");

        if (!inputFields.classList.contains("has-inputs")) {
            inputFields.classList.add("has-inputs");
          }

    //     inputFields.innerHTML += `
    //     <div class="input-container">
    //         <input type = "date" placeholder = "Start Date (YYYY-MM-DD)" class = "input-field short">
    //         <input type = "text" placeholder = "Duration (days)" class = "input-field short">
    //         <input type = "text" placeholder = "Symptoms: mood, acne, bloating, etc." class = "input-field full">
    //         <button class = "plus-button">+</button>
    //     </div>
    // `;
            inputFields.innerHTML += `
            <div class="input-container">
            <div class="input-row">
                <input type="date" placeholder="Start Date (YYYY-MM-DD)" class="input-field short">
                <input type="text" placeholder="Duration (days)" class="input-field short">
            </div>
            <input type="text" placeholder="Symptoms: mood, acne, bloating, etc." class="input-field full" maxlength="150">
            <button class="plus-button" onclick = "logPdFn()">+</button>
            </div>
        `;
    addCountIndex++;
    }
}

let addCountPeriods = 0;
function addFnPeriods() {
    if (addCountPeriods === 0) {
        const inputFields = document.getElementById("inputFields");

        if (!inputFields.classList.contains("has-inputs")) {
            inputFields.classList.add("has-inputs");
          }

        inputFields.innerHTML += `
        <div class="input-container period-input-container">
            <input type = "date" placeholder = "Start Date (YYYY-MM-DD)" class = "input-field short">
            <input type = "text" placeholder = "Duration (days)" class = "input-field short">
            <input type = "text" placeholder = "Symptoms: mood, acne, bloating, etc." class = "input-field full" maxlength="150">
            <button class = "plus-button" onclick = "logPdFn()">Add</button>
        </div>
    `;
    addCountPeriods++;
    }
}

