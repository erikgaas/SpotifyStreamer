// Generated code from Butter Knife. Do not modify!
package com.example.android.spotifystreamer;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class DetailActivityFragment$$ViewInjector<T extends com.example.android.spotifystreamer.DetailActivityFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492947, "field 'listView'");
    target.listView = finder.castView(view, 2131492947, "field 'listView'");
  }

  @Override public void reset(T target) {
    target.listView = null;
  }
}
