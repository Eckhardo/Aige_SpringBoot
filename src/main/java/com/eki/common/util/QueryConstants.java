package com.eki.common.util;


public final class QueryConstants {

	public static final String HOST = "http://localhost:";

	public static final String APP_ROOT = "nre";

	public static final String QUESTIONMARK = "?";

	public static final String PAGE_NO = "pageNo";
	public static final String PAGE_SIZE = "pageSize";
	public static final String SORT_BY = "sortBy";
	public static final String SORT_ORDER = "sortOrder";

	public static final String ID = "id"; // is constant because it's used for the controller mapping
	public static final String DESC = "DESC"; // is constant because it's used for the controller mapping
	public static final String SLASH = "/";
	public static final String AMPERSAND = "&";
	public static final String SEPARATOR = ",";

	public static final String OP = "=";
	public static final String QUESTION_MARK = "?";
	public static final String ONE = "1";
	public static final String FIVE = "5";
	public static final String COMPLETE_SORT_ORDER = QUESTION_MARK + SORT_BY + OP + ID + AMPERSAND + SORT_ORDER + OP
			+ DESC;
	public static final String COMPLETE_PAGE_REQUEST = QUESTION_MARK + PAGE_NO + OP + ONE + AMPERSAND + PAGE_SIZE+OP
			+ FIVE;

	private QueryConstants() {
		throw new AssertionError();
	}
}