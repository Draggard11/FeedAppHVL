<script>
    import {login} from '../../services/auth.js'
    import {userId, username} from '../../userStore.js'
    import {get} from 'svelte/store'

    let email = ""
    let password = ""
    let message = ""
    let isLoading = false;

    const handleLogin = async (event) => {
        event.preventDefault();
        isLoading = true;

        try {
            let response = await login(get(username), email, password);
            message = response.id;
            userId.set(response.userId);
        } catch(error) {
            message = error.message;
        } finally {
            isLoading = false;
        }
    };
</script>

<h1>login</h1>
<form on:submit|preventDefault={handleLogin}>
    <input type="text" placeholder="username" bind:value={$username} required />
    <input type="email" placeholder="Email" bind:value={email}  required/>
    <input type="password" placeholder="Password" bind:value={password}  required/>
    <button type="submit" disabled="{isLoading}">{isLoading ? "logging in..." : "login"}</button>
</form>

{#if message}
    <p>{message}</p>
{/if}