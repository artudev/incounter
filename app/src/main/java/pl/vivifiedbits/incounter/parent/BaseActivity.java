package pl.vivifiedbits.incounter.parent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.vivifiedbits.incounter.R;
import timber.log.Timber;

/**
 * Created by Artur Kasprzak on 27.11.2017.
 */
public class BaseActivity extends AppCompatActivity
		implements FragmentManager.OnBackStackChangedListener {

	@BindView(R.id.fl_content)
	FrameLayout mFlContent;

	@BindView(R.id.toolbar)
	Toolbar mToolbar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Timber.d("onCreate");
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_base);
		ButterKnife.bind(this);

		setSupportActionBar(mToolbar);

		getSupportFragmentManager().addOnBackStackChangedListener(this);
		shouldDisplayHomeUp();
	}

	@Override
	public void setContentView(@LayoutRes int layoutResID) {
		Timber.d("setContentView");
		getLayoutInflater().inflate(layoutResID, mFlContent, true);
	}

	@Override
	public boolean onSupportNavigateUp() {
		boolean fragmentPoped = getSupportFragmentManager().popBackStackImmediate();
		if (fragmentPoped) {
			hideKeyboard();
			return true;
		}

		Intent parent = getParentActivityIntent();
		if (parent != null) {
			NavUtils.navigateUpFromSameTask(this);
		} else {
			finishAffinity();
		}
		hideKeyboard();
		return true;
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	@Override
	public void onBackStackChanged() {
		shouldDisplayHomeUp();
	}

	protected void shouldDisplayHomeUp() {
		boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
		canGoBack = canGoBack || getParentActivityIntent() != null;
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
			if (canGoBack) {
				actionBar.setDisplayHomeAsUpEnabled(canGoBack);
			}
		}
		Timber.d("setDisplayHomeAsUpEnabled " + canGoBack);
	}

	public void hideKeyboard() {
		View focus = getCurrentFocus();
		InputMethodManager imm =
				(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (focus != null) {
			imm.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * Replace current fragment
	 */
	public void setContent(BaseFragment noveltyFrag) {
		getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		setFragment(noveltyFrag, false);
	}

	/**
	 * Replace current fragment as a parent-child pattern. Pressing back will go back to replaced
	 * fragment.
	 */
	public void setChildContent(BaseFragment newFragment) {
		setFragment(newFragment, true);
	}

	/**
	 * Adds fragment as a parent-child pattern. Pressing back will go back to covered fragment.
	 */
	public void addChildContent(BaseFragment newFragment) {
		addFragment(newFragment, true);
	}

	private void addFragment(BaseFragment newFragment, boolean child) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction
				.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
						android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		if (child) {
			fragmentTransaction.addToBackStack(null);
		}
		fragmentTransaction.add(R.id.fl_content, newFragment);
		fragmentTransaction.commit();
		hideKeyboard();
	}

	private void setFragment(BaseFragment newFragment, boolean child) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction
				.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
						android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		if (child) {
			fragmentTransaction.addToBackStack(null);
		}
		fragmentTransaction.replace(R.id.fl_content, newFragment);
		fragmentTransaction.commit();
		hideKeyboard();
	}

	/**
	 * Creates path of fragment with first fragment as root, rest as child-child-...
	 */
	public void setContentPath(ArrayList<BaseFragment> newFrags) {
		hideKeyboard();
		for (int i = 0; i < newFrags.size(); i++) {
			BaseFragment frag = newFrags.get(i);
			if (i == 0) {
				setContent(frag);
			} else {
				setChildContent(frag);
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		super.onResume();
		hideKeyboard();
	}

	public Toolbar getToolbar() {
		return mToolbar;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
