package com.tvtracker.model;

/**
 * 
 * @authors Austin Forry, Daniel Muckerman, Chris Yealy, Austin Decker
 *
 */

public class MediaListProgress {
	private MediaProgress mediaProgress;
	private MediaList mediaList;

	public MediaListProgress(MediaList mediaList, MediaProgress mediaProgress) {
		this.mediaList = mediaList;
		this.mediaProgress = mediaProgress;
	}

	public void addMedia(Media media, int episodesSeenInCurrentSeason, int currentSeason) {
		mediaList.addMedia(media);
		mediaProgress.addMedia(media, new Progress(episodesSeenInCurrentSeason, currentSeason));
	}

	public Progress currentProgress(Media media) {
		return mediaProgress.getProgress(media);
	}
}
