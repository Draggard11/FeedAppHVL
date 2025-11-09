<script>
    import {login} from '../../services/auth.js'

    let username = ""
    let email = ""
    let password = ""
    let message = ""
    let isLoading = false;

    const handleLogin = async (event) => {
        event.preventDefault();

        isLoading = true;

        try {
            let response = await login(username, email, password);
            message = response.message;
            window.location.href = "/me";
        } catch (error) {
            message = error.message;
        } finally {
            isLoading = false;
        }
    };
</script>

<h1>login</h1>
<form on:submit|preventDefault={handleLogin}>
    <input type="text" placeholder="username" bind:value={username} required />
    <input type="email" placeholder="Email" bind:value={email}  required/>
    <input type="password" placeholder="Password" bind:value={password}  required/>
    <button type="submit" disabled="{isLoading}">{isLoading ? "logging in..." : "login"}</button>
</form>

{#if message}
    <p>{message}</p>
{/if}