/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.ui.workflow.action;

import org.apache.hop.core.database.DatabaseMeta;
import org.apache.hop.core.logging.ILoggingObject;
import org.apache.hop.core.logging.LoggingObjectType;
import org.apache.hop.core.logging.SimpleLoggingObject;
import org.apache.hop.core.variables.IVariables;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.metadata.api.IHopMetadataProvider;
import org.apache.hop.pipeline.transform.ITransform;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.dialog.MessageBox;
import org.apache.hop.ui.core.gui.WindowProperty;
import org.apache.hop.ui.core.widget.MetaSelectionLine;
import org.apache.hop.workflow.WorkflowMeta;
import org.apache.hop.workflow.action.IActionDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

/**
 * The ActionDialog class is responsible for constructing and opening the settings dialog for the
 * action. Whenever the user opens the action settings in HopGui, it will instantiate the dialog
 * class passing in the IAction object and call the
 *
 * <pre>
 * open()
 * </pre>
 *
 * <p>method on the dialog. SWT is the native windowing environment of HopGui, and it is typically
 * the framework used for implementing action dialogs.
 */
public abstract class ActionDialog extends Dialog implements IActionDialog {
  private static final Class<?> PKG = ITransform.class;

  /** The loggingObject for the dialog */
  public static final ILoggingObject loggingObject =
      new SimpleLoggingObject("Action dialog", LoggingObjectType.ACTION_DIALOG, null);

  /** The Metadata provider */
  protected IHopMetadataProvider metadataProvider;

  /** The variables for the action dialogs */
  protected IVariables variables;

  /** The workflow metadata object. */
  protected WorkflowMeta workflowMeta;

  /** A reference to the properties user interface */
  protected PropsUi props;

  /** A reference to the shell */
  protected Shell shell;

  /**
   * Instantiates a new action dialog.
   *
   * @param parent the parent shell
   * @param workflowMeta the workflow metadata object
   */
  public ActionDialog(Shell parent, WorkflowMeta workflowMeta, IVariables variables) {
    super(parent, SWT.NONE);
    this.props = PropsUi.getInstance();
    this.variables = variables;
    this.workflowMeta = workflowMeta;
  }

  public void setActive() {
    if (shell != null && !shell.isDisposed()) {
      shell.setActive();
    }
  }

  public void dispose() {
    props.setScreen(new WindowProperty(shell));
    shell.dispose();
  }

  public boolean isDisposed() {
    if (shell != null) {
      return shell.isDisposed();
    }
    return true;
  }

  /**
   * Adds the connection line for the given parent and previous control, and returns a meta
   * selection manager control
   *
   * @param parent the parent composite object
   * @param previous the previous control
   * @param selected The selected database connection
   * @param lsMod changed listener
   * @return the combo box UI component
   */
  public MetaSelectionLine<DatabaseMeta> addConnectionLine(
      Composite parent, Control previous, DatabaseMeta selected, ModifyListener lsMod) {

    final MetaSelectionLine<DatabaseMeta> wConnection =
        new MetaSelectionLine<>(
            variables,
            metadataProvider,
            DatabaseMeta.class,
            parent,
            SWT.NONE,
            BaseMessages.getString(PKG, "BaseTransformDialog.Connection.Label"),
            BaseMessages.getString(PKG, "BaseTransformDialog.Connection.Tooltip"));
    wConnection.addToConnectionLine(parent, previous, selected, lsMod);
    return wConnection;
  }

  /**
   * Adds the connection line for the given parent and previous control, and returns a meta
   * selection manager control
   *
   * @param parent the parent composite object
   * @param previous the previous control
   * @param connection
   * @param lsMod
   * @return the combo box UI component
   */
  public MetaSelectionLine<DatabaseMeta> addConnectionLine(
      Composite parent, Control previous, String connection, ModifyListener lsMod) {

    DatabaseMeta databaseMeta = getWorkflowMeta().findDatabase(connection, variables);
    // If we are unable to find the database metadata, display only a warning message so that the
    // user
    // can proceed to correct the issue in the affected pipeline
    if (databaseMeta == null) {
      MessageBox mb = new MessageBox(shell, SWT.OK | SWT.ICON_WARNING);
      mb.setMessage(
          BaseMessages.getString(
              PKG,
              "BaseTransformDialog.InvalidConnection.DialogMessage",
              variables.resolve(connection)));
      mb.setText(BaseMessages.getString(PKG, "BaseTransformDialog.InvalidConnection.DialogTitle"));
      mb.open();
    }
    return addConnectionLine(parent, previous, databaseMeta, lsMod);
  }

  public IHopMetadataProvider getMetadataProvider() {
    return metadataProvider;
  }

  public void setMetadataProvider(IHopMetadataProvider metadataProvider) {
    this.metadataProvider = metadataProvider;
  }

  public WorkflowMeta getWorkflowMeta() {
    return workflowMeta;
  }
}
