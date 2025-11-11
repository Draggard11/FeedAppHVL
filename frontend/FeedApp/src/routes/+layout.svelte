<script>
  import '../app.css'
  import {logout} from '../services/auth.js'
</script>

<nav>
  <a href="/">home</a>
  <a href="/login">login</a>
  <a href="/register">register</a>
  <button onClick={logout}>logout</button>
</nav>

<slot />

<style>
    nav button {
        position: absolute;
        top: 10px;
        right: 10px;
    }
</style>