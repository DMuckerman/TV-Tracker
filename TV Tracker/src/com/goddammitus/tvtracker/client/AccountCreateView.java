package com.goddammitus.tvtracker.client;

import com.goddammitus.tvtracker.server.model.persist.DatabaseProvider;
import com.goddammitus.tvtracker.server.model.persist.FakeDatabase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.tvtracker.model.Account;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class AccountCreateView extends Composite implements IsWidget{
	private TextBox txtCreateUsername;
	private PasswordTextBox txtCreatePassword;
	Label lblError;

	public AccountCreateView() {
		LayoutPanel panel = new LayoutPanel();
		initWidget(panel);

		Label lblCreateAnAccount = new Label("Create An Account");
		panel.add(lblCreateAnAccount);
		panel.setWidgetLeftWidth(lblCreateAnAccount, 160.0, Unit.PX, 119.0, Unit.PX);
		panel.setWidgetTopHeight(lblCreateAnAccount, 21.0, Unit.PX, 18.0, Unit.PX);

		Label lblCreateUsername = new Label("Create Username:");
		panel.add(lblCreateUsername);
		panel.setWidgetLeftWidth(lblCreateUsername, 35.0, Unit.PX, 112.0, Unit.PX);
		panel.setWidgetTopHeight(lblCreateUsername, 87.0, Unit.PX, 18.0, Unit.PX);

		Label lblCreatePassword = new Label("Create Password:");
		panel.add(lblCreatePassword);
		panel.setWidgetLeftWidth(lblCreatePassword, 35.0, Unit.PX, 112.0, Unit.PX);
		panel.setWidgetTopHeight(lblCreatePassword, 146.0, Unit.PX, 18.0, Unit.PX);

		txtCreateUsername = new TextBox();
		panel.add(txtCreateUsername);
		panel.setWidgetLeftWidth(txtCreateUsername, 160.0, Unit.PX, 173.0, Unit.PX);
		panel.setWidgetTopHeight(txtCreateUsername, 81.0, Unit.PX, 34.0, Unit.PX);

		txtCreatePassword = new PasswordTextBox();
		panel.add(txtCreatePassword);
		panel.setWidgetLeftWidth(txtCreatePassword, 160.0, Unit.PX, 173.0, Unit.PX);
		panel.setWidgetTopHeight(txtCreatePassword, 140.0, Unit.PX, 34.0, Unit.PX);

		Button btnSubmit = new Button("Submit");
		btnSubmit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				handleCreate();
			}
		});
		panel.add(btnSubmit);
		panel.setWidgetLeftWidth(btnSubmit, 160.0, Unit.PX, 81.0, Unit.PX);
		panel.setWidgetTopHeight(btnSubmit, 208.0, Unit.PX, 30.0, Unit.PX);

		lblError = new Label("That account already exists, pick another! ");
		lblError.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(lblError);
		panel.setWidgetLeftWidth(lblError, 83.0, Unit.PX, 318.0, Unit.PX);
		panel.setWidgetTopHeight(lblError, 267.0, Unit.PX, 15.0, Unit.PX);

		Button btnCancel = new Button("Cancel");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				handleCancel();
			}
		});
		panel.add(btnCancel);
		panel.setWidgetLeftWidth(btnCancel, 252.0, Unit.PX, 81.0, Unit.PX);
		panel.setWidgetTopHeight(btnCancel, 208.0, Unit.PX, 30.0, Unit.PX);
		lblError.setVisible(false);
	}

	public void handleCreate() {
		if (txtCreateUsername.getValue().isEmpty() || txtCreatePassword.getValue().isEmpty()) {
			lblError.setText("You must supply both a username and password.");
			lblError.setVisible(true);
			return;
		}
		System.out.println(txtCreateUsername.getValue() + " loves java");
		RPC.loginService.checkIfAccountExists(txtCreateUsername.getValue(), new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if (result == true) {
					lblError.setText("That account already exists, pick another! ");
					lblError.setVisible(true);
				} else {
					lblError.setVisible(false);
					RPC.loginService.createAccount(txtCreateUsername.getValue(), txtCreatePassword.getValue(), new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							lblError.setText("Couldn't add username to database");
							lblError.setVisible(true);
						}
						@Override
						public void onSuccess(Boolean result) {
							// Hooray, we added a user to the database, go back to the login view
							LoginView view = new LoginView();
							TV_Tracker.setView(view);
						}
					});
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				lblError.setText("Could not check if account exists.");
				lblError.setVisible(true);
			}
		});
	}

	public void handleCancel() {
		LoginView view = new LoginView();
		TV_Tracker.setView(view);
	}
}
