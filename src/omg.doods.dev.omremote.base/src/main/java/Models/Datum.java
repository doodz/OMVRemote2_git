package Models;

/**
 * Created by thiba on 29/08/2016.
 */
public class Datum
{
    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private String title;

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    private boolean enabled;

    public boolean getEnabled() { return this.enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    private boolean running;

    public boolean getRunning() { return this.running; }

    public void setRunning(boolean running) { this.running = running; }
}