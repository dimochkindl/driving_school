function toggleDarkMode() {
    var styleLink = document.getElementById('style-link');

    if (styleLink.getAttribute('href') === '/css/default_style.css') {
        styleLink.setAttribute('href', '/css/dark_theme.css');
    } else {
        styleLink.setAttribute('href', '/css/default_style.css');
    }
}

function enableDarkMode() {
    document.documentElement.classList.add('dark_theme');
}

function disableDarkMode() {
    document.documentElement.classList.remove('dark_theme');
}

window.matchMedia('(prefers-color-scheme: dark)').addListener(e => {
    if (e.matches) {
        enableDarkMode();
    } else {
        disableDarkMode();
    }
});
