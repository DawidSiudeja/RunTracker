 <h1>RunTracker</h1>
    <p>RunTracker is an activity tracking app for runners, built with modern Android technologies such as Jetpack Compose, MVVM architecture, and Google Maps API. It allows users to track their running routes, view them in real-time on an interactive map, and store essential statistics in a Room database.</p>

  <h2>Features</h2>
    <ul>
        <li>Real-time GPS-based run tracking.</li>
        <li>Displaying the user’s running route on a map using Google Map Compose.</li>
        <li>Utilizing Foreground Service – even if the user closes the app or sends it to the background, run tracking will continue.</li>
        <li>Room database for storing and managing run statistics.</li>
        <li>Jetpack Navigation Component for nested navigation, deep linking, and conditional navigation to the onboarding screen.</li>
        <li>Weekly statistics with filterable charts.</li>
    </ul>

  <h2>Project Structure</h2>
    <ul>
        <li><strong>data:</strong> Contains classes related to entities and the database.</li>
        <li><strong>di:</strong> Hilt modules.</li>
        <li><strong>domain:</strong> Models and use cases.</li>
        <li><strong>gps:</strong> Responsible for handling run tracking.</li>
        <li><strong>navigation:</strong> Navigation within the app.</li>
        <li><strong>presentation:</strong> User Interface Design and ViewModel Logic</li>
    </ul>

  <h2>Technologies</h2>
    <ul>
        <li><strong>Kotlin:</strong> As the programming language.</li>
        <li><strong>Jetpack Compose:</strong> For building the UI.</li>
        <li><strong>Jetpack Navigation:</strong> For screen navigation and deep linking.</li>
        <li><strong>Room:</strong> For storing and managing run statistics.</li>
        <li><strong>Google Maps API:</strong> For tracking running activities, including speed, distance, and route display.</li>
        <li><strong>Hilt:</strong> For dependency injection.</li>
        <li><strong>Coil:</strong> For asynchronous image loading.</li>
    </ul>

  <h2>Installation</h2>
    <p>Clone this project and open it in Android Studio.</p>

  <h2>Integrating with Google Maps</h2>
    <p>If you want to display Google Maps, follow these steps:</p>
    <ol>
        <li>Create and obtain a Google Maps API key using the official guide.</li>
        <li>Open the <strong>AndroidManifest.xml</strong> file.</li>
        <li>Add your API key as follows:</li>
    </ol>
<pre><code>android:name="com.google.android.geo.API_KEY"
android:value="YOUR_API_KEY"</code></pre>

<h2>Presenation</h2>
<a target="_blank" href="https://www.youtube.com/watch?v=GujzpPYK5lA">Youtube link</a>
