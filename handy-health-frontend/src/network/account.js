import network from './network_config.json';

const ACCOUNT_SERVICE_ORIGIN = network.network.account.origin;

const LOCAL_STORAGE_AUTH_KEY = "auth";
const LOCAL_STORAGE_RAUTH_KEY = "rauth";

export async function signup(firstName, lastName, username, password) {
    const api = ACCOUNT_SERVICE_ORIGIN + network.network.account.methods.signup;
    const response = await fetch(api, {method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify({ firstName, lastName, credentials: {username, password}})});
    return response.ok ? response.json() : {};
}

export async function signin(username, password) {
    const api = ACCOUNT_SERVICE_ORIGIN + network.network.account.methods.signin;
    const response = await fetch(api, {method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify({ credentials: {username, password}})});
    return response.ok ? response.json() : { accessToken: {} };
}

export function signout() {
    localStorage.removeItem(LOCAL_STORAGE_AUTH_KEY);
    localStorage.removeItem(LOCAL_STORAGE_RAUTH_KEY);
}

export async function authenticate(username, password) {
    const authResponse = await signin(username, password);
    if (authResponse.accessToken.tokenValue && authResponse.refreshToken) {
        localStorage.setItem(LOCAL_STORAGE_AUTH_KEY, authResponse.accessToken.tokenValue);
        localStorage.setItem(LOCAL_STORAGE_RAUTH_KEY, authResponse.refreshToken);
        return true;
    }
    return false;
}

export function isAuthenticated() {
    return localStorage.getItem(LOCAL_STORAGE_AUTH_KEY) && localStorage.getItem(LOCAL_STORAGE_RAUTH_KEY);
}

export async function validateToken(token) {
    const api = ACCOUNT_SERVICE_ORIGIN + network.network.account.methods.validateToken;
    const response = await fetch(`${api}?accessToken=${token}`);
    return response.ok ? response.json() : false;
}

export function getAccessToken() {
    return localStorage.getItem(LOCAL_STORAGE_AUTH_KEY);
}

export function getRefreshToken() {
    return localStorage.getItem(LOCAL_STORAGE_RAUTH_KEY);
}

export async function ifAccessAllow(then, els) {
    const validAccess = await validateToken(getAccessToken());

    if (validAccess) {
        then();
        return true;
    }

    const isRefresh = await refreshAccessToken();

    if (isRefresh) {
        then();
    } else {
        els();
    }

    return isRefresh;
}

export async function refreshAccessToken() {
    const api = ACCOUNT_SERVICE_ORIGIN + network.network.account.methods.refreshToken;
    const response = await fetch(`${api}?refreshToken=${getRefreshToken()}`, {method: "POST"});
    if (response.ok) {
        localStorage.setItem(LOCAL_STORAGE_AUTH_KEY, (await response.json()).tokenValue);
        return true;
    }
    return false;
}

export async function getAccountMe() {
    const api = ACCOUNT_SERVICE_ORIGIN + network.network.account.methods.accountMe;
    const response = await fetch(api, {headers: {Authorization: 'Bearer ' + getAccessToken()}});
    return response.ok ? (await response.json()) : {};
}

export async function getDoctors() {
    const api = ACCOUNT_SERVICE_ORIGIN + network.network.account.methods.doctors;
    const response = await fetch(api, {headers: {Authorization: 'Bearer ' + getAccessToken()}});
    return response.ok ? (await response.json()) : {};
}

export async function getAccountsByRole(role, page, size) {
    const api = ACCOUNT_SERVICE_ORIGIN + network.network.account.methods.getByRole.replace(":role", role);
    const response = await fetch(`${api}?page=${page}&size=${size}`, {headers: {Authorization: 'Bearer ' + getAccessToken()}});
    return response.ok ? (await response.json()) : {};
}

export async function getDoctorAccounts(filterFullName, page, size) {
    const api = ACCOUNT_SERVICE_ORIGIN + network.network.account.methods.getDoctorAccounts;
    const response = await fetch(`${api}?filterName=${encodeURI(filterFullName)}&page=${page}&size=${size}`, {headers: {Authorization: 'Bearer ' + getAccessToken()}});
    return response.ok ? (await response.json()) : {};
}

export async function getAccounts(page, size) {
    const api = ACCOUNT_SERVICE_ORIGIN + network.network.account.methods.getAccounts;
    const response = await fetch(`${api}?page=${page}&size=${size}`, {headers: {Authorization: 'Bearer ' + getAccessToken()}});
    return response.ok ? (await response.json()) : {};
}

export async function setAccount(accountId, account) {
    const api = ACCOUNT_SERVICE_ORIGIN + network.network.account.methods.setAccount.replace(":aid", accountId);
    const response = await fetch(api, {method: "PUT", headers: {Authorization: 'Bearer ' + getAccessToken(), "Content-Type": "application/json"}, body: JSON.stringify(account)});
    return response.ok ? (await response.json()) : {};
}