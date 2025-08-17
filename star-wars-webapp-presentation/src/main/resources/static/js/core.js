document.addEventListener('DOMContentLoaded', function() {
    const characterSearchInput = document.getElementById('characterSearchInput');
    const characterSearchButton = document.getElementById('characterSearchButton');
    const characterClearButton = document.getElementById('characterClearButton');

    const starshipSearchInput = document.getElementById('starshipSearchInput');
    const starshipSearchButton = document.getElementById('starshipSearchButton');
    const starshipClearButton = document.getElementById('starshipClearButton');

    function updateCharacterButtonStates() {
        if (!characterSearchInput || !characterSearchButton || !characterClearButton) return;

        const hasText = characterSearchInput.value.trim() !== '';
        characterSearchButton.disabled = !hasText;

        if (hasText) {
            characterClearButton.classList.remove('disabled');
        } else {
            characterClearButton.classList.add('disabled');
        }
    }

    function updateStarshipButtonStates() {
        if (!starshipSearchInput || !starshipSearchButton || !starshipClearButton) return;

        const hasText = starshipSearchInput.value.trim() !== '';
        starshipSearchButton.disabled = !hasText;

        if (hasText) {
            starshipClearButton.classList.remove('disabled');
        } else {
            starshipClearButton.classList.add('disabled');
        }
    }

    // Set initial state (only if elements exist)
    updateCharacterButtonStates();
    updateStarshipButtonStates();

    // Update on input changes (guarded with null check)
    if (characterSearchInput) {
        characterSearchInput.addEventListener('input', updateCharacterButtonStates);
    }

    if (starshipSearchInput) {
        starshipSearchInput.addEventListener('input', updateStarshipButtonStates);
    }
});
