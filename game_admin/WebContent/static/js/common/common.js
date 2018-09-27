function err(target, message) {
	var t = $(target);
	if (t.hasClass('textbox-text')) {
		t = t.parent();
	}
	var m = t.next('.error-message');
	if (!m.length) {
		m = $('<div class="error-message"></div>').insertAfter(t);
	}
	m.html(message);
}
