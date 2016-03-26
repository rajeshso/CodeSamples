package hello;

public class Greeting {

    private long id;
    private String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

	/**
	 * 
	 */
	public Greeting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(long id) {
		this.id = id;
	}
}
