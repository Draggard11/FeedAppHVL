<script>
    let { children } = $props();
</script>

<nav>
    <a href="/login">login</a>
    <a href="/register">register</a>
</nav>

{@render children()}
