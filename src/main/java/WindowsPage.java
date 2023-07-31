import java.util.Set;

public class WindowsPage {
    String windowNew = null;
    public void windowCheckUrl (String windowOriginal, Set<String> currentWindows) {
        for (String window : currentWindows) {
            if (!window.equals(windowOriginal)) {
                windowNew = window;
            }
        }
    }
}