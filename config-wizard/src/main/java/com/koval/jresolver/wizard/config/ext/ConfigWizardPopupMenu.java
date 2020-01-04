package com.koval.jresolver.wizard.config.ext;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;


public class ConfigWizardPopupMenu extends JPopupMenu {

  public ConfigWizardPopupMenu() {
    super();
    Action cut = new DefaultEditorKit.CutAction();
    cut.putValue(Action.NAME, "Cut");
    cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
    add(cut);

    Action copy = new DefaultEditorKit.CopyAction();
    copy.putValue(Action.NAME, "Copy");
    copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
    add(copy);

    Action paste = new DefaultEditorKit.PasteAction();
    paste.putValue(Action.NAME, "Paste");
    paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
    add(paste);
  }
}
