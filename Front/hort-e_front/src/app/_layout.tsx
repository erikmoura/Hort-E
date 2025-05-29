import { Stack } from 'expo-router';
import 'react-native-reanimated';

export const unstable_settings = {
  initialRouteName: 'screens/(landing)/landing',
};

export default function Layout() {
  return (
    <Stack
      screenOptions={{
        headerShown: false,
        contentStyle: { backgroundColor: '#f1f3f4' },
      }}
    >
      <Stack.Screen name="screens/(landing)/landing" options={{ headerShown: false }} />
      <Stack.Screen name="screens/(auth)/login" options={{ headerShown: false }} />
      <Stack.Screen name="screens/(auth)/register" options={{ headerShown: false }} />
      <Stack.Screen name="screens/(tabs)" options={{ headerShown: false }} />
    </Stack>
  );
}
