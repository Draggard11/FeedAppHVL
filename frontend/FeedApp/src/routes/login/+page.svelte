<script>
    import {login} from '../../services/auth.js'
    import {userId, username} from '../../userStore.js'

    let email = ""
    let password = ""
    let message = ""
    let isLoading = false;

    const handleLogin = async () => {
        isLoading = true;
        message = '';
        try {
            const response = await login($username, email, password);
            message = response.message;
            if (response.id != null) userId.set(response.id);
        } catch(error) {
            message = error.message;
        } finally {
            isLoading = false;
        }
    };
</script>

<h1>login</h1>
<div class="card box">
    <form on:submit|preventDefault={handleLogin}>
        <input class="cred" type="text" placeholder="username" bind:value={$username} required />
        <input class="cred" type="email" placeholder="Email" bind:value={email}  required/>
        <input class="cred" type="password" placeholder="Password" bind:value={password}  required/>
        <button type="submit" disabled="{isLoading}">{isLoading ? "logging in..." : "login"}</button>
    </form>
</div>

{#if message}
    <p>{message}</p>
{/if}