document.addEventListener('DOMContentLoaded', function() {
    const characterSearchInput = document.getElementById('characterSearchInput');
    const characterSearchButton = document.getElementById('characterSearchButton');
    const characterClearButton = document.getElementById('characterClearButton');

    // Function to update button states
    function updateButtonStates() {
        const hasText = characterSearchInput.value.trim() !== '';
        characterSearchButton.disabled = !hasText;

        if (hasText) {
            characterClearButton.classList.remove('disabled');
        } else {
            characterClearButton.classList.add('disabled');
        }
    }

    // Set initial state
    updateButtonStates();

    // Update on input changes
    characterSearchInput.addEventListener('input', updateButtonStates);
});
