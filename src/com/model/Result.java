package com.model;

public class Result {
	
	    private Integer id;
		private String theWord;
		private int theCount;

		public Result(String w, int c) {
			theWord = w;
			theCount = c;
		}
		public Result() {
			
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		
		public String getTheWord() {
			return theWord;
		}

		public void setTheWord(String theWord) {
			this.theWord = theWord;
		}

		public int getTheCount() {
			return theCount;
		}

		public void setTheCount(int theCount) {
			this.theCount = theCount;
		}

	
	}


