package com.goddammitus.tvtracker.client;
import java_cup.internal_error;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tvtracker.model.Account;
import com.tvtracker.model.Login;
import com.tvtracker.model.Media;
import com.tvtracker.model.MediaListProgress;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class AddMediaView extends Composite implements IsWidget {
	private Account user;
	private TextBox txtName;
	private TextBox txtCurrentSeason;
	private TextBox txtEpisodesWatched;
	private TextBox txtTotalEpisodes;
	private Label lblError;


	public AddMediaView(Account user) {
		this.user = user;
		String headerMessage = "Adding media for " + user.getName();

		LayoutPanel panel = new LayoutPanel();
		initWidget(panel);

		Label lblWelcome = new Label(headerMessage);
		panel.add(lblWelcome);
		panel.setWidgetLeftWidth(lblWelcome, 19.0, Unit.PX, 250.0, Unit.PX);
		panel.setWidgetTopHeight(lblWelcome, 12.0, Unit.PX, 18.0, Unit.PX);

		Label lblName = new Label("Name:");
		panel.add(lblName);
		panel.setWidgetLeftWidth(lblName, 109.0, Unit.PX, 40.0, Unit.PX);
		panel.setWidgetTopHeight(lblName, 49.0, Unit.PX, 15.0, Unit.PX);

		Label lblCurrentSeason = new Label("Current Season:");
		panel.add(lblCurrentSeason);
		panel.setWidgetLeftWidth(lblCurrentSeason, 55.0, Unit.PX, 112.0, Unit.PX);
		panel.setWidgetTopHeight(lblCurrentSeason, 96.0, Unit.PX, 15.0, Unit.PX);

		Label lblCurrentEpisodes = new Label("Current episodes:");
		panel.add(lblCurrentEpisodes);
		panel.setWidgetLeftWidth(lblCurrentEpisodes, 47.0, Unit.PX, 112.0, Unit.PX);
		panel.setWidgetTopHeight(lblCurrentEpisodes, 149.0, Unit.PX, 15.0, Unit.PX);

		Label lblNewLatotalEpisodesbel = new Label("Total Episodes:");
		panel.add(lblNewLatotalEpisodesbel);
		panel.setWidgetLeftWidth(lblNewLatotalEpisodesbel, 61.0, Unit.PX, 114.0, Unit.PX);
		panel.setWidgetTopHeight(lblNewLatotalEpisodesbel, 195.0, Unit.PX, 15.0, Unit.PX);

		txtName = new TextBox();
		txtName.setWidth("149px");
		panel.add(txtName);
		panel.setWidgetLeftWidth(txtName, 168.0, Unit.PX, 159.0, Unit.PX);
		panel.setWidgetTopHeight(txtName, 39.0, Unit.PX, 31.0, Unit.PX);

		txtCurrentSeason = new TextBox();
		txtCurrentSeason.setWidth("149px");
		panel.add(txtCurrentSeason);
		panel.setWidgetLeftWidth(txtCurrentSeason, 168.0, Unit.PX, 159.0, Unit.PX);
		panel.setWidgetTopHeight(txtCurrentSeason, 88.0, Unit.PX, 31.0, Unit.PX);

		txtEpisodesWatched = new TextBox();
		txtEpisodesWatched.setWidth("149px");
		panel.add(txtEpisodesWatched);
		panel.setWidgetLeftWidth(txtEpisodesWatched, 168.0, Unit.PX, 159.0, Unit.PX);
		panel.setWidgetTopHeight(txtEpisodesWatched, 142.0, Unit.PX, 31.0, Unit.PX);

		txtTotalEpisodes = new TextBox();
		txtTotalEpisodes.setWidth("149px");
		panel.add(txtTotalEpisodes);
		panel.setWidgetLeftWidth(txtTotalEpisodes, 168.0, Unit.PX, 159.0, Unit.PX);
		panel.setWidgetTopHeight(txtTotalEpisodes, 188.0, Unit.PX, 31.0, Unit.PX);

		Button btnSubmit = new Button("Submit");
		btnSubmit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				handleSubmit();
			}
		});
		panel.add(btnSubmit);
		panel.setWidgetLeftWidth(btnSubmit, 101.0, Unit.PX, 81.0, Unit.PX);
		panel.setWidgetTopHeight(btnSubmit, 255.0, Unit.PX, 27.0, Unit.PX);

		Button btnCancel = new Button("Cancel");
		panel.add(btnCancel);
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				handleCancel();
			}
		});
		panel.setWidgetLeftWidth(btnCancel, 223.0, Unit.PX, 81.0, Unit.PX);
		panel.setWidgetTopHeight(btnCancel, 255.0, Unit.PX, 27.0, Unit.PX);

		lblError = new Label("ERROR");
		lblError.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lblError.setVisible(false);
		panel.add(lblError);
		panel.setWidgetLeftWidth(lblError, 55.0, Unit.PX, 334.0, Unit.PX);
		panel.setWidgetTopHeight(lblError, 230.0, Unit.PX, 15.0, Unit.PX);
	}

	public void addMediaToAccount(int account_id, String name, int currentSeason, int episodesWatched, int totalEpisodes) {
		this.user.getMediaListProgress().addMedia(new Media(name), episodesWatched, totalEpisodes, currentSeason);
	}

	public int getAccountId() {
		return this.user.getId();
	}

	public void handleSubmit() {
		if (txtName.getValue().isEmpty() || txtCurrentSeason.getValue().isEmpty() || txtEpisodesWatched.getValue().isEmpty() || txtTotalEpisodes.getValue().isEmpty()) {
			lblError.setText("Please fill in all fields");
			lblError.setVisible(true);
			return;
		}
		RPC.loginService.addMedia(this.user.getId(), txtName.getValue(), Integer.parseInt(txtCurrentSeason.getValue()), Integer.parseInt(txtEpisodesWatched.getValue()), Integer.parseInt(txtTotalEpisodes.getValue()), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Error
				lblError.setText("Couldn't add the media to a user's account");
				lblError.setVisible(true);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result == true) {
					addMediaToAccount(getAccountId(), txtName.getValue(), Integer.parseInt(txtCurrentSeason.getValue()), Integer.parseInt(txtEpisodesWatched.getValue()), Integer.parseInt(txtTotalEpisodes.getValue()));
					TV_Tracker.setView(new HomePageView(user));
				} else {
					lblError.setText("Couldn't add the media to a user's account");
					lblError.setVisible(true);
				}
			}

		});
	}

	public void handleCancel() {
		TV_Tracker.setView(new HomePageView(user));
	}
}
