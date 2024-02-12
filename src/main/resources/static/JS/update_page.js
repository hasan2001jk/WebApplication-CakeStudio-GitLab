function apnut_tolko_1_raz() {
    if (!window.location.hash) {
        window.location = window.location + '#uge_obnovleno';
        window.location.reload();
    }
}
setTimeout("apnut_tolko_1_raz()", 1599);