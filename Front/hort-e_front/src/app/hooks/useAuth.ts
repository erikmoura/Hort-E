// hooks/useAuth.ts
import * as SecureStore from 'expo-secure-store';
import { useCallback, useEffect, useState } from 'react';


const AUTH_TOKEN_KEY = 'authToken';

export function useAuth() {
  const [token, setToken] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  // Carrega o token ao iniciar o app
  useEffect(() => {
    const loadToken = async () => {
      const storedToken = await SecureStore.getItemAsync('authToken');
      setToken(storedToken);
      setLoading(false);
    };
    loadToken();
  }, []);

  const login = useCallback(async (jwtToken: string) => {
    await SecureStore.setItemAsync(AUTH_TOKEN_KEY, jwtToken);
    setToken(jwtToken);
  }, []);

  const logout = useCallback(async () => {
    await SecureStore.deleteItemAsync('authToken');
    setToken(null);
  }, []);

  return {
    token,
    isAuthenticated: !!token,
    loading,
    login,
    logout,
  };
}
