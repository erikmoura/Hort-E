import React from 'react';
import {Tabs} from 'expo-router';
import { Ionicons } from '@expo/vector-icons';
import { Dimensions } from 'react-native';
import AllGuides from './guides/allguides';

const { height, width } = Dimensions.get('window');

export default function TabsLayout() {
    return (
    <Tabs
      initialRouteName='guides/allguides'
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
            height: height * 0.08 ,
            width: width * 0.95,
            paddingTop: height * 0.02,
            elevation: 2,
        },
      }}
    >
      <Tabs.Screen
        name="profile"
        options={{
          tabBarIcon: ({ color, size }) => (
            <Ionicons name="list" size={size} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="guides/allguides"
        options={{
          tabBarIcon: ({ color, size }) => (
            <Ionicons name="list" size={size} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="plantlist/plantlist"
        options={{
          tabBarIcon: ({ color, size }) => (
            <Ionicons name="list" size={size} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="community"
        options={{
          tabBarIcon: ({ color, size }) => (
            <Ionicons name="list" size={size} color={color} />
          ),
        }}
      />
    </Tabs>
  );
}