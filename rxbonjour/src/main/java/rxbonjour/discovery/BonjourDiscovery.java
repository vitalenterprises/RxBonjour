package rxbonjour.discovery;

import android.content.Context;
import android.os.Build;

import rxbonjour.utils.BonjourUtils;
import rxbonjour.model.BonjourEvent;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

/**
 * Base interface for DNS-SD implementations
 */
public abstract class BonjourDiscovery<T extends BonjourUtils<?>> {

	protected final T utils;

	public BonjourDiscovery() {
		this.utils = createUtils();
	}

	protected abstract T createUtils();

	/**
	 * Starts a Bonjour service discovery for the provided service type.
	 *
	 * @param context Context of the request
	 * @param type    Type of service to discover
	 * @return An Observable for Bonjour events
	 */
	public abstract rx.Observable<BonjourEvent> start(Context context, String type);

	public static BonjourDiscovery get(boolean forceNsdManager) {
		if (forceNsdManager && Build.VERSION.SDK_INT >= JELLY_BEAN) {
			return new JBBonjourDiscovery();
		} else {
			return new SupportBonjourDiscovery();
		}
	}
}
