// Generated code from Butter Knife. Do not modify!
package com.example.android.spotifystreamer;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MainActivityFragment$$ViewInjector<T extends com.example.android.spotifystreamer.MainActivityFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492949, "field 'searchView'");
    target.searchView = finder.castView(view, 2131492949, "field 'searchView'");
    view = finder.findRequiredView(source, 2131492950, "field 'listView'");
    target.listView = finder.castView(view, 2131492950, "field 'listView'");
  }

  @Override public void reset(T target) {
    target.searchView = null;
    target.listView = null;
  }
}
