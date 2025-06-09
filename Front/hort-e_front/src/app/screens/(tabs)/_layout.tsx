import { Ionicons } from '@expo/vector-icons';
import { Tabs } from 'expo-router';
import React from 'react';
import { Dimensions, View } from 'react-native';

const { height, width } = Dimensions.get('window');

const renderIcon = (name: keyof typeof Ionicons.glyphMap) => {
  return ({ color, size, focused }: { color: string; size: number; focused: boolean }) => (
    <View
      style={{
        position: 'absolute',
        height: height * 0.06,
        width: height * 0.06,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: focused ? '#257D3C' : 'transparent',
        borderRadius: 15,
      }}
    >
      <Ionicons name={name} size={size} color={focused ? '#FFFFFF' : color} />
    </View>
  );
};

export default function TabsLayout() {
  return (
    <Tabs
      initialRouteName="guides/allguides"
      screenOptions={{
        headerShown: false,
        tabBarActiveTintColor: '#257D3C',
        tabBarInactiveTintColor: '#000000',
        tabBarShowLabel: false,
        tabBarStyle: {
          position: 'absolute',
          bottom: height * 0.04,
          marginLeft: width * 0.025,
          backgroundColor: '#fff',
          borderRadius: 32,
          height: height * 0.08,
          width: width * 0.95,
          paddingTop: height * 0.02,
          elevation: 2,
        },
      }}
    >
      <Tabs.Screen
        name="profile"
        options={{
          tabBarIcon: renderIcon('person-sharp'),
        }}
      />
      <Tabs.Screen
        name="guides/allguides"
        options={{
          tabBarIcon: renderIcon('book-sharp'),
        }}
      />
      <Tabs.Screen
        name="plantlist/plantlist"
        options={{
          tabBarIcon: renderIcon('list-sharp'),
        }}
      />
      <Tabs.Screen
        name="community"
        options={{
          tabBarIcon: renderIcon('people-sharp'),
        }}
      />
    </Tabs>
  );
}
