package model;

	public  class TestResultData {
        public String testKey;
        public String status;
        public String comment;
        public String start;
        public String finish;

        public TestResultData(String testKey, String status, String comment, String start, String finish) {
            this.testKey = testKey;
            this.status = status;
            this.comment = comment;	         
            this.start = start;
            this.finish = finish;
        }
    }

