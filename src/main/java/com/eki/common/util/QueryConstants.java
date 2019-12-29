package com.eki.common.util;

public final class QueryConstants {

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

	private QueryConstants() {
		throw new AssertionError();
	}
}