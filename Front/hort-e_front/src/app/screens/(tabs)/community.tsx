import { useRouter } from "expo-router";
import React, { useEffect, useState } from "react";
import { Alert, Dimensions, ScrollView, StyleSheet, View } from "react-native";
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
    }
})
