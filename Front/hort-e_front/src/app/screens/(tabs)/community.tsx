import { Ionicons as Icon } from '@expo/vector-icons';
import { useRouter } from "expo-router";
import React, { useEffect, useState } from "react";
import { Alert, Dimensions, ScrollView, StyleSheet, TouchableOpacity, View } from "react-native";
import BannerTopo from "../../components/BannerTopo";
import Post from "../../components/Post";
import { useAuth } from "../../hooks/useAuth";

const { height, width } = Dimensions.get("window");

export default function Community() {

    const router = useRouter();
    const { token } = useAuth();

    const [posts, setPosts] = useState([]);

    useEffect(() => {
        if (!token) return;

        const fetchPosts = async () => {
            try {
                const response = await fetch('http://10.0.2.2:8080/api/posts', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                });

                if (!response.ok) {
                    throw new Error('Erro ao buscar posts');
                }

                const data = await response.json();
                setPosts(data);
            } catch (error) {
                console.error(error);
                Alert.alert('Erro', 'Não foi possível carregar os posts.');
            }
        };

        fetchPosts();

    }, [token]);


    return (
        <View style={styles.container}>
            <View style={styles.estiloBanner}>
                <BannerTopo title="Comunidade" />
            </View>
            <ScrollView style={styles.scrollview}>
                {posts.map((post) => (
                    <Post 
                        key={post.id} 
                        post={post} 
                    />
                ))}
                <View style={styles.blocoInvisivel}/>
            </ScrollView>
            <View style={styles.adicionarPost}>
                <TouchableOpacity 
                    style={styles.bolinhaPost} 
                >
                    <Icon name="add" size={30} color="#fff" />
                </TouchableOpacity>
            </View>
        </View>

    );
};

const styles = StyleSheet.create({
    container: {
        display: 'flex',
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F8F8F8'
    },
    estiloBanner: {
        position: 'absolute',
        top: 0,
        width: '100%',
    },
    scrollview: {
        marginTop: height * 0.12,
    },
    blocoInvisivel: {
        height: height * 0.2,
        width: '100%'
    },
    adicionarPost: {
        position: 'absolute',
        borderRadius: 9999,
        bottom: height * 0.135,
        right: width * 0.067,
        elevation: 5,
        boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    },
    bolinhaPost: {
        borderRadius: 9999,
        width: width * 0.15,
        height: width * 0.15,
        backgroundColor: '#257D3C',
        justifyContent: 'center',
        alignItems: 'center',
    }
})
