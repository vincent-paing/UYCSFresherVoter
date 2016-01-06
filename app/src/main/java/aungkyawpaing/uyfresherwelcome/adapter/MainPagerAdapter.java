package aungkyawpaing.uyfresherwelcome.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import aungkyawpaing.uyfresherwelcome.fragments.VotingFragment;

/**
 * Created by vincentpaing on 17/12/15.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

  public MainPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    return VotingFragment.newInstance(position);
  }

  @Override public int getCount() {
    return 2;
  }

  @Override public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return "King";
      case 1:
        return "Queen";
      default:
        return super.getPageTitle(position);
    }
  }
}
