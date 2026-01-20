package io.github.painfu11y.buildnotifier.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class OnChangeDocumentListener implements DocumentListener {

    protected abstract void onChange();

    @Override
    public void insertUpdate(DocumentEvent e) {
        onChange();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onChange();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        onChange();
    }
}
