#include <bits/stdc++.h>
#define X first
#define Y second
#define pb push_back
#define ll long long
#define ll long long
#define pii pair<int, int>
#define pll pair<long long, long long>
#define vi vector<int>
#define vll vector<long long>
#define mii map<int, int>
#define si set<int>
#define sc set<char>
#define MOD 1000000007
#define PI 3.1415926535897932384626433832795

using namespace std;

void indef(){
#ifndef ONLINE_JUDGE
  //freopen("input.txt","r",stdin);
  freopen("level1.txt","w",stdout);
#endif
}
int Map[30][30];
void solve() {
  
  for (int i = 0; i < 30; i++){
    for (int j = 0; j < 30; j++){
      Map[i][j] = 1;
    }
  }

  for (int i = 0; i < 30; i++){
    for (int j = 0; j < 30; j++){
      if ( i == 0 || j == 0 || i == 29 || j == 29 ){
        Map [i][j] = 2;
      }
    }
  }

  for (int i = 20; i < 24; i++){
    for (int j = 6; j < 9; j++){
      Map[i][j] = 3;
    }
  }

  
  for (int i = 8; i < 14; i++){
    for (int j = 20; j < 23; j++){
      Map[i][j] = 4;
    }
  }

  for (int i = 0; i < 30; i++){
    for (int j = 0; j < 30; j++){
      cout << Map[i][j];
    }
    cout << endl;
  }

}
 
int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  indef();

  int t = 1;
  //cin >> t;

  while (t--) {
    solve();
  }

  return 0;
}
