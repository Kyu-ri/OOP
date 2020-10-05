////////////////////////////////////////////////////////////////////////////////
//
// File: virtualLego.cpp
//
// Original Author: 박창현 Chang-hyeon Park, 
// Modified by Bong-Soo Sohn and Dong-Jun Kim
// 
// Originally programmed for Virtual LEGO. 
// Modified later to program for Virtual Billiard.
//        
////////////////////////////////////////////////////////////////////////////////

#include "d3dUtility.h"
#include <vector>
#include <ctime>
#include <cstdlib>
#include <cstdio>
#include <cassert>
#include "MyVector.h"
//#include <Windows.h>
//#include <tchar.h>

//#pragma comment(lib, "d3d9.lib")
//#pragma commnet(lib, "d3dx9.lib")

IDirect3DDevice9* Device = NULL;

// window size
const int Width = 1024;
const int Height = 768;

// There are four balls
// initialize the position (coordinate) of each ball (ball0 ~ ball3)
const float spherePos[4][2] = { { -2.7f,0 } ,{ +2.4f,0 } ,{ 3.3f,0 },{ -2.7f,-0.9f } }; //{-2.7f,0} , {+2.4f,0} , {3.3f,0}, {-2.7f,-0.9f}
																						//{4.5f, -3.06f} , {-4.5f,3.06f} , {-4.5f, -3.06f} , {4.5f, 3.06f }
																						// initialize the color of each ball (ball0 ~ ball3)
const D3DXCOLOR sphereColor[4] = { d3d::RED, d3d::RED, d3d::YELLOW, d3d::WHITE };


// ------------------------------------------------------------------------------
// Score Variable
// ------------------------------------------------------------------------------
ID3DXFont* text = NULL;

RECT whiteScoreTextRect = { 100, 50, Width, Height };
RECT yellowScoreTextRect = { 730, 50, Width, Height };

RECT whiteScoreRect = { 280, 50, Width, Height };
RECT yellowScoreRect = { 920, 50, Width, Height };

int whiteScore = 0;
int yellowScore = 0;

bool whiteGetScore = false;
bool yellowGetScore = false;

char*  whiteScoreText = new char[100];
char* yellowScoreText = new char[100];

// -----------------------------------------------------------------------------
// Turn Variable
// -----------------------------------------------------------------------------
const bool bWHITE = true;
const bool bYELLOW = false;

bool playerTurn = bWHITE;
bool isBallMoving = false;

bool start = false;

// -----------------------------------------------------------------------------
// Trace Variabel
// -----------------------------------------------------------------------------
bool hint = false;
int whiteHintCount = 10;
int yellowHintCount = 10;

char* whiteHintText = new char[100];
char* yellowHintText = new char[100];
#define VK_HINT 0x41
RECT whiteHintRect = { 100, 80, Width, Height };
RECT yellowHintRect = { 730, 80, Width, Height };

RECT whiteHintCountRect = { 260, 80, Width, Height };
RECT yellowHintCountRect = { 900, 80, Width, Height };


// -----------------------------------------------------------------------------
// Time Variable
// -----------------------------------------------------------------------------
RECT timeRect = { 400, 20, Width, Height };

char* timeText = new char[1000];

//clock_t begin, end;
time_t begin, end, dif;

int playTime = 0;

// -----------------------------------------------------------------------------
// Texture variable 
// -----------------------------------------------------------------------------
/*LPCTSTR lpszClassName = _T("Direct X");
LPDIRECT3DTEXTURE9 g_tex = NULL;
int g_tex_width = 0;
int g_tex_height = 0;
LPD3DXSPRITE g_pd3dSprite = NULL;*/

// -----------------------------------------------------------------------------
// Transform matrices
// -----------------------------------------------------------------------------
D3DXMATRIX g_mWorld;
D3DXMATRIX g_mView;
D3DXMATRIX g_mProj;

#define M_RADIUS 0.21   // ball radius
#define PI 3.14159265
#define M_HEIGHT 0.01
#define DECREASE_RATE 0.9982
#define SCORE 10
#define VK_RESET 0x52
#define VK_S 0x53

// -----------------------------------------------------------------------------
// CSphere class definition
// -----------------------------------------------------------------------------

class CSphere {
private:
	double					center_x, center_y, center_z;
	float                   m_radius;
	double					m_velocity_x;
	double					m_velocity_z;
	MyVector				v;

	bool	is_intersected_wall[4];
	bool	is_intersected_ball[4];

	int		ballNum;

	bool	redBall[2] = { false, false };
	bool	penalty = false;
	bool	noInterect = true;

public:
	CSphere(void)
	{
		D3DXMatrixIdentity(&m_mLocal);
		ZeroMemory(&m_mtrl, sizeof(m_mtrl));
		m_radius = 0;
		m_velocity_x = 0;
		m_velocity_z = 0;
		m_pSphereMesh = NULL;
		v = MyVector();
	}
	~CSphere(void) {}

public:

	bool create(IDirect3DDevice9* pDevice, D3DXCOLOR color = d3d::WHITE)
	{
		if (NULL == pDevice)
			return false;

		m_mtrl.Ambient = color;
		m_mtrl.Diffuse = color;
		m_mtrl.Specular = color;
		m_mtrl.Emissive = d3d::BLACK;
		m_mtrl.Power = 5.0f;

		if (FAILED(D3DXCreateSphere(pDevice, getRadius(), 50, 50, &m_pSphereMesh, NULL)))
			return false;
		return true;
	}

	void destroy(void)
	{
		if (m_pSphereMesh != NULL) {
			m_pSphereMesh->Release();
			m_pSphereMesh = NULL;
		}
	}

	void draw(IDirect3DDevice9* pDevice, const D3DXMATRIX& mWorld)
	{
		if (NULL == pDevice)
			return;
		pDevice->SetTransform(D3DTS_WORLD, &mWorld);
		pDevice->MultiplyTransform(D3DTS_WORLD, &m_mLocal);
		pDevice->SetMaterial(&m_mtrl);
		m_pSphereMesh->DrawSubset(0);
	}

	bool hasIntersected(CSphere& ball)
	{
		if (sqrt(pow((ball.getCenter().x - this->getCenter().x), 2) + pow((ball.getCenter().y - this->getCenter().y), 2) +
			pow((ball.getCenter().z - this->getCenter().z), 2)) <= getRadius() * 2) return true;
		return false;
	}

	virtual void hitBy(CSphere& ball) {
		if (hasIntersected(ball)) {
			if (!this->is_intersected_ball[ball.ballNum] || !ball.is_intersected_ball[this->ballNum]) {
				this->noInterect = false;
				ball.noInterect = false;

				this->initIntersect();
				ball.initIntersect();

				this->is_intersected_ball[ball.ballNum] = true;
				ball.is_intersected_ball[this->ballNum] = true;

				this->setDirection(ball);

				//this->setAngle(cos(PI / 4), 0, cos(PI / 4));
				
				//num가 높은 애가 ball 낮은 애가 this
				//white : only ball, yellow : white와 부딪혔을 때만 this
				if (!playerTurn) {//white턴일 때
					if (ball.ballNum == 3) {
						if (this->ballNum == 2) ball.penalty = true;
						else if (this->ballNum == 1) ball.redBall[1] = true;
						else if (this->ballNum == 0) ball.redBall[0] = true;
						else {}
					}
				}
				else {
					if (this->ballNum == 2 && ball.ballNum == 3) this->penalty = true;
					if (ball.ballNum == 2) {
						if (this->ballNum == 1) ball.redBall[1] = true;
						else if (this->ballNum == 0) ball.redBall[0] = true;
						else {}
					}
				}
				
				
				//tempX = a, v = b

				/*double tempX = this->getCenter().x - ball.getCenter().x;
				double tempZ = this->getCenter().z - ball.getCenter().z;

				double dx1 = ((tempX * ball.getVelocity_X() + tempZ * ball.getVelocity_Z()) / (tempX * tempX  + tempZ * tempZ))*(tempX);
				double dz1 = ((tempX * ball.getVelocity_X() + tempZ * ball.getVelocity_Z()) / (tempX * tempX  + tempZ * tempZ))*(tempZ);

				double tx1 = ball.getVelocity_X() - dx1;
				double tz1 = ball.getVelocity_Z() - dz1;

				tempX = ball.getCenter().x - this->getCenter().x;
				tempZ = ball.getCenter().z - this->getCenter().z;

				double dx2 = ((tempX * this->getVelocity_X() + tempZ * this->getVelocity_Z()) / (tempX * tempX + tempZ * tempZ))*(tempX);
				double dz2 = ((tempX * this->getVelocity_X() + tempZ * this->getVelocity_Z()) / (tempX * tempX + tempZ * tempZ))*(tempZ);

				double tx2 = this->getVelocity_X() - dx2;
				double tz2 = this->getVelocity_Z() - dz2;

				ball.setPower(dx2 + tx1, dz2 + tz1);
				this->setPower(dx1 + tx2, dz1 + tz2);*/

				//num가 높은 애가 ball 낮은 애가 this
				//white : only ball, yellow : white와 부딪혔을 때만 this

				
			}
		}
	}

	void ballUpdate(float timeDiff)
	{
		const float TIME_SCALE = 3.3;
		D3DXVECTOR3 cord = this->getCenter();
		double vx = abs(this->getVelocity_X());
		double vz = abs(this->getVelocity_Z());

		/*double vx = abs(this->v.getX());
		double vz = abs(this->v.getZ());*/

		if (vx > 0.01 || vz > 0.01) {
			float tX = cord.x + TIME_SCALE*timeDiff*m_velocity_x;
			float tZ = cord.z + TIME_SCALE*timeDiff*m_velocity_z;
	
//			float tX = cord.x + TIME_SCALE*timeDiff*v.getX();
//			float tZ = cord.z + TIME_SCALE*timeDiff*v.getZ();

			//correction of position of ball
			// Please uncomment this part because this correction of ball position is necessary when a ball collides with a wall
			/*if(tX >= (4.5 - M_RADIUS))
			tX = 4.5 - M_RADIUS;
			else if(tX <=(-4.5 + M_RADIUS))
			tX = -4.5 + M_RADIUS;
			else if(tZ <= (-3 + M_RADIUS))
			tZ = -3 + M_RADIUS;
			else if(tZ >= (3 - M_RADIUS))
			tZ = 3 - M_RADIUS;*/

			this->setCenter(tX, cord.y, tZ);
		}
		else { this->setPower(0, 0); }
		//this->setPower(this->getVelocity_X() * DECREASE_RATE, this->getVelocity_Z() * DECREASE_RATE);
		double rate = 1 - (1 - DECREASE_RATE)*timeDiff * 400;
		if (rate < 0)
			rate = 0;
		this->setPower(getVelocity_X() * rate, getVelocity_Z() * rate);
	}

	double getVelocity_X() { return this->m_velocity_x; }
	double getVelocity_Z() { return this->m_velocity_z; }

	//double getVelocity_X() { return v.getX(); }
	//double getVelocity_Z() { return v.getZ(); }

	void setPower(double vx, double vz) {
		this->m_velocity_x = vx;
		this->m_velocity_z = vz;
		v.setVector(vx, vz);
	}

	void setPower(MyVector v) {
		this->setPower(v.getX(), v.getZ());
	}

	void setCenter(double x, double y, double z) {
		D3DXMATRIX m;
		center_x = x;	center_y = y;	center_z = z;
		D3DXMatrixTranslation(&m, x, y, z);
		setLocalTransform(m);
	}

	void setCenter(D3DXVECTOR3 org) {
		setCenter(org.x, org.y, org.z);
	}

	float getRadius(void)  const { return (float)(M_RADIUS); }

	const D3DXMATRIX& getLocalTransform(void) const { return m_mLocal; }

	void setLocalTransform(const D3DXMATRIX& mLocal) { m_mLocal = mLocal; }

	D3DXVECTOR3 getCenter(void) const {
		D3DXVECTOR3 org(center_x, center_y, center_z);
		return org;
	}

	void initIntersect() {
		for (int i = 0; i < 4; i++) {
			this->is_intersected_wall[i] = false;
			this->is_intersected_ball[i] = false;
		}
	}

	void allInit() {
		this->penalty = false;
		this->noInterect = true;
		for (int i = 0; i < 2; i++) this->redBall[i] = false;
		this->initIntersect();
	}

	void setIntersectWall(int wallNum) { is_intersected_wall[wallNum] = true; }
	void setIntersectBall(int ballNum) { is_intersected_ball[ballNum] = true; }
	const bool* getIntersectWall() { return is_intersected_wall; }
	const bool* getIntersectBall() { return is_intersected_ball; }
	int getBallNum() const { return ballNum; }

	bool getpenalty() const { return this->penalty || this->noInterect; }
	const bool* getRedBall() { return this->redBall; }
	MyVector getVector() { return v; }

	void setNum(int num) { this->ballNum = num; }

	void setDirection(CSphere& ball) {
		MyVector d1 = this->v.orthogonal(MyVector(ball.getCenter().x - this->getCenter().x, ball.getCenter().z - this->getCenter().z));
		MyVector t1 = this->v.tVector(d1);

		MyVector d2 = ball.v.orthogonal(MyVector(this->getCenter().x - ball.getCenter().x, this->getCenter().z - ball.getCenter().z));
		MyVector t2 = ball.v.tVector(d2);

		this->setPower(t1 + d2);
		ball.setPower(t2 + d1);


		/*double tempX = this->getCenter().x - ball.getCenter().x;
		double tempZ = this->getCenter().z - ball.getCenter().z;

		double dx1 = ((tempX * ball.getVelocity_X() + tempZ * ball.getVelocity_Z()) / (tempX * tempX + tempZ * tempZ))*(tempX);
		double dz1 = ((tempX * ball.getVelocity_X() + tempZ * ball.getVelocity_Z()) / (tempX * tempX + tempZ * tempZ))*(tempZ);

		double tx1 = ball.getVelocity_X() - dx1;
		double tz1 = ball.getVelocity_Z() - dz1;

		tempX = ball.getCenter().x - this->getCenter().x;
		tempZ = ball.getCenter().z - this->getCenter().z;

		double dx2 = ((tempX * this->getVelocity_X() + tempZ * this->getVelocity_Z()) / (tempX * tempX + tempZ * tempZ))*(tempX);
		double dz2 = ((tempX * this->getVelocity_X() + tempZ * this->getVelocity_Z()) / (tempX * tempX + tempZ * tempZ))*(tempZ);

		double tx2 = this->getVelocity_X() - dx2;
		double tz2 = this->getVelocity_Z() - dz2;

		ball.setPower(dx2 + tx1, dz2 + tz1);
		this->setPower(dx1 + tx2, dz1 + tz2);*/
	}

	/*void setAngle(float angleX, float angleY, float angleZ) {
		D3DXMatrixRotationX(&m_mLocal, angleX);
		D3DXMatrixRotationX(&m_mLocal, angleY);
		D3DXMatrixRotationX(&m_mLocal, angleZ);
	}*/
protected:
	D3DXMATRIX              m_mLocal;
	D3DMATERIAL9            m_mtrl;
	ID3DXMesh*              m_pSphereMesh;

};

class Trace : public CSphere{
public :
	Trace() {
		CSphere();

	}

	virtual void hitBy(CSphere& ball) {
		if (hasIntersected(ball)) {
			if (!this->getIntersectBall()[ball.getBallNum()]) {
				this->initIntersect();
				this->setIntersectBall(ball.getBallNum());

				this->setDirection(ball);
			}
		}
	}

	void setDirection(CSphere& ball) {
		MyVector d1 = this->getVector().orthogonal(MyVector(ball.getCenter().x - this->getCenter().x, ball.getCenter().z - this->getCenter().z));
		MyVector t1 = this->getVector().tVector(d1);

		this->setPower(t1);
	}
};


// -----------------------------------------------------------------------------
// CWall class definition
// -----------------------------------------------------------------------------

class CWall {

private:

	float					m_x;
	float					m_z;
	float                   m_width;
	float                   m_depth;
	float					m_height;

public:
	CWall(void)
	{
		D3DXMatrixIdentity(&m_mLocal);
		ZeroMemory(&m_mtrl, sizeof(m_mtrl));
		m_width = 0;
		m_depth = 0;
		m_pBoundMesh = NULL;
	}
	~CWall(void) {}
public:
	bool create(IDirect3DDevice9* pDevice, float ix, float iz, float iwidth, float iheight, float idepth, D3DXCOLOR color = d3d::WHITE)
	{
		if (NULL == pDevice)
			return false;

		m_mtrl.Ambient = color;
		m_mtrl.Diffuse = color;
		m_mtrl.Specular = color;
		m_mtrl.Emissive = d3d::BLACK;
		m_mtrl.Power = 5.0f;

		m_width = iwidth;
		m_depth = idepth;

		if (FAILED(D3DXCreateBox(pDevice, iwidth, iheight, idepth, &m_pBoundMesh, NULL)))
			return false;
		return true;
	}
	void destroy(void)
	{
		if (m_pBoundMesh != NULL) {
			m_pBoundMesh->Release();
			m_pBoundMesh = NULL;
		}
	}
	void draw(IDirect3DDevice9* pDevice, const D3DXMATRIX& mWorld)
	{
		if (NULL == pDevice)
			return;
		pDevice->SetTransform(D3DTS_WORLD, &mWorld);
		pDevice->MultiplyTransform(D3DTS_WORLD, &m_mLocal);
		pDevice->SetMaterial(&m_mtrl);
		m_pBoundMesh->DrawSubset(0);
	}

	bool hasIntersected(CSphere& ball) {
		if ((ball.getCenter().x >= 4.44f - ball.getRadius() || ball.getCenter().x <= -4.44f + ball.getRadius()) ||
			(ball.getCenter().z >= 3.00f - ball.getRadius() || ball.getCenter().z <= -3.00f + ball.getRadius())) return true;
		return false;
	}

	void hitBy(CSphere& ball)
	{
		if (hasIntersected(ball)) {
			float vx = ball.getVelocity_X();
			float vz = ball.getVelocity_Z();
			if (ball.getCenter().z > 3.00f - ball.getRadius() && !ball.getIntersectWall()[0]) {
				ball.initIntersect();
				ball.setIntersectWall(0);
				ball.setPower(vx, -(vz));
			}
			else if (ball.getCenter().z < -3.00f + ball.getRadius() && !ball.getIntersectWall()[1]) {
				ball.initIntersect();
				ball.setIntersectWall(1);
				ball.setPower(vx, -(vz));
			}
			else if (ball.getCenter().x > 4.44f - ball.getRadius() && !ball.getIntersectWall()[2]) {
				ball.initIntersect();
				ball.setIntersectWall(2);
				ball.setPower(-(vx), vz);
			}
			else if (ball.getCenter().x < -4.44f + ball.getRadius() && !ball.getIntersectWall()[3]) {
				ball.initIntersect();
				ball.setIntersectWall(3);
				ball.setPower(-(vx), vz);
			}
			else {}

		}
		// Insert your code here.
	}

	void setPosition(float x, float y, float z)
	{
		D3DXMATRIX m;
		this->m_x = x;
		this->m_z = z;

		D3DXMatrixTranslation(&m, x, y, z);
		setLocalTransform(m);
	}

	float getHeight(void) const { return M_HEIGHT; }

	bool operator==(const CWall& a) {
		if (a.m_x == this->m_x && a.m_z == this->m_z) return true;
		else return false;
	}


private:
	void setLocalTransform(const D3DXMATRIX& mLocal) { m_mLocal = mLocal; }

	D3DXMATRIX              m_mLocal;
	D3DMATERIAL9            m_mtrl;
	ID3DXMesh*              m_pBoundMesh;
};

// -----------------------------------------------------------------------------
// CLight class definition
// -----------------------------------------------------------------------------

class CLight {
public:
	CLight(void)
	{
		static DWORD i = 0;
		m_index = i++;
		D3DXMatrixIdentity(&m_mLocal);
		::ZeroMemory(&m_lit, sizeof(m_lit));
		m_pMesh = NULL;
		m_bound._center = D3DXVECTOR3(0.0f, 0.0f, 0.0f);
		m_bound._radius = 0.0f;
	}
	~CLight(void) {}
public:
	bool create(IDirect3DDevice9* pDevice, const D3DLIGHT9& lit, float radius = 0.1f)
	{
		if (NULL == pDevice)
			return false;
		if (FAILED(D3DXCreateSphere(pDevice, radius, 10, 10, &m_pMesh, NULL)))
			return false;

		m_bound._center = lit.Position;
		m_bound._radius = radius;

		m_lit.Type = lit.Type;
		m_lit.Diffuse = lit.Diffuse;
		m_lit.Specular = lit.Specular;
		m_lit.Ambient = lit.Ambient;
		m_lit.Position = lit.Position;
		m_lit.Direction = lit.Direction;
		m_lit.Range = lit.Range;
		m_lit.Falloff = lit.Falloff;
		m_lit.Attenuation0 = lit.Attenuation0;
		m_lit.Attenuation1 = lit.Attenuation1;
		m_lit.Attenuation2 = lit.Attenuation2;
		m_lit.Theta = lit.Theta;
		m_lit.Phi = lit.Phi;
		return true;
	}
	void destroy(void)
	{
		if (m_pMesh != NULL) {
			m_pMesh->Release();
			m_pMesh = NULL;
		}
	}
	bool setLight(IDirect3DDevice9* pDevice, const D3DXMATRIX& mWorld)
	{
		if (NULL == pDevice)
			return false;

		D3DXVECTOR3 pos(m_bound._center);
		D3DXVec3TransformCoord(&pos, &pos, &m_mLocal);
		D3DXVec3TransformCoord(&pos, &pos, &mWorld);
		m_lit.Position = pos;

		pDevice->SetLight(m_index, &m_lit);
		pDevice->LightEnable(m_index, TRUE);
		return true;
	}

	void draw(IDirect3DDevice9* pDevice)
	{
		if (NULL == pDevice)
			return;
		D3DXMATRIX m;
		D3DXMatrixTranslation(&m, m_lit.Position.x, m_lit.Position.y, m_lit.Position.z);
		pDevice->SetTransform(D3DTS_WORLD, &m);
		pDevice->SetMaterial(&d3d::WHITE_MTRL);
		m_pMesh->DrawSubset(0);
	}

	D3DXVECTOR3 getPosition(void) const { return D3DXVECTOR3(m_lit.Position); }



private:
	DWORD               m_index;
	D3DXMATRIX          m_mLocal;
	D3DLIGHT9           m_lit;
	ID3DXMesh*          m_pMesh;
	d3d::BoundingSphere m_bound;
};


// -----------------------------------------------------------------------------
// Global variables
// -----------------------------------------------------------------------------
CWall	g_legoPlane;
CWall	g_legowall[4];
CSphere	g_sphere[4];
CSphere	g_target_blueball;
CLight	g_light;
Trace trace = Trace();


double g_camera_pos[3] = { 0.0, 5.0, -8.0 };

// -----------------------------------------------------------------------------
// Functions
// -----------------------------------------------------------------------------


void destroyAllLegoBlock(void)
{
}


// initialization
bool Setup()
{
	int i;
	time(&begin);

	whiteScoreText[0] = '0';
	yellowScoreText[0] = '0';
	whiteScoreText[1] = '\0';
	yellowScoreText[1] = '\0';
	//whitepenalty[0] = '\0';
	//yellowpenalty[0] = '\0';


	D3DXMatrixIdentity(&g_mWorld);
	D3DXMatrixIdentity(&g_mView);
	D3DXMatrixIdentity(&g_mProj);

	// create plane and set the position
	if (false == g_legoPlane.create(Device, -1, -1, 9, 0.03f, 6, d3d::GREEN)) return false;
	g_legoPlane.setPosition(0.0f, -0.0006f / 5, 0.0f);

	// create walls and set the position. note that there are four walls
	if (false == g_legowall[0].create(Device, -1, -1, 9, 0.3f, 0.12f, d3d::DARKRED)) return false;
	g_legowall[0].setPosition(0.0f, 0.12f, 3.06f);
	if (false == g_legowall[1].create(Device, -1, -1, 9, 0.3f, 0.12f, d3d::DARKRED)) return false;
	g_legowall[1].setPosition(0.0f, 0.12f, -3.06f);
	if (false == g_legowall[2].create(Device, -1, -1, 0.12f, 0.3f, 6.24f, d3d::DARKRED)) return false;
	g_legowall[2].setPosition(4.56f, 0.12f, 0.0f);
	if (false == g_legowall[3].create(Device, -1, -1, 0.12f, 0.3f, 6.24f, d3d::DARKRED)) return false;
	g_legowall[3].setPosition(-4.56f, 0.12f, 0.0f);

	// create four balls and set the position
	for (i = 0; i<4; i++) {
		if (false == g_sphere[i].create(Device, sphereColor[i])) return false;
		g_sphere[i].setCenter(spherePos[i][0], (float)M_RADIUS, spherePos[i][1]);
		g_sphere[i].setPower(0, 0);
		g_sphere[i].setNum(i);
		g_sphere[i].initIntersect();
	}
	if (false == trace.create(Device, d3d::BLACK)) return false;
	trace.setPower(0, 0);

	// create blue ball for set direction
	if (false == g_target_blueball.create(Device, d3d::BLUE)) return false;
	g_target_blueball.setCenter(.0f, (float)M_RADIUS, .0f);

	// light setting 
	D3DLIGHT9 lit;
	::ZeroMemory(&lit, sizeof(lit));
	lit.Type = D3DLIGHT_POINT;
	lit.Diffuse = d3d::WHITE;
	lit.Specular = d3d::WHITE * 0.9f;
	lit.Ambient = d3d::WHITE * 0.9f;
	lit.Position = D3DXVECTOR3(0.0f, 3.0f, 0.0f);
	lit.Range = 100.0f;
	lit.Attenuation0 = 0.0f;
	lit.Attenuation1 = 0.9f;
	lit.Attenuation2 = 0.0f;
	if (false == g_light.create(Device, lit))
		return false;

	// Position and aim the camera.
	D3DXVECTOR3 pos(0.0f, 5.0f, -8.0f);
	D3DXVECTOR3 target(0.0f, 0.0f, 0.0f);
	D3DXVECTOR3 up(0.0f, 2.0f, 0.0f);
	D3DXMatrixLookAtLH(&g_mView, &pos, &target, &up);
	Device->SetTransform(D3DTS_VIEW, &g_mView);

	// Set the projection matrix.
	D3DXMatrixPerspectiveFovLH(&g_mProj, D3DX_PI / 4,
		(float)Width / (float)Height, 1.0f, 100.0f);
	Device->SetTransform(D3DTS_PROJECTION, &g_mProj);

	// Set render states.
	Device->SetRenderState(D3DRS_LIGHTING, TRUE);
	Device->SetRenderState(D3DRS_SPECULARENABLE, TRUE);
	Device->SetRenderState(D3DRS_SHADEMODE, D3DSHADE_GOURAUD);

	g_light.setLight(Device, g_mWorld);

	// Font 초기화 / 생성
	D3DXFONT_DESCA lf2;
	ZeroMemory(&lf2, sizeof(D3DXFONT_DESCA));
	lf2.Height = 25;
	lf2.Width = 13;
	lf2.Weight = 80;
	lf2.MipLevels = 0;
	lf2.Italic = false;
	lf2.CharSet = 0;
	lf2.OutputPrecision = 0;
	lf2.Quality = 0;
	lf2.PitchAndFamily = 0;
	strcpy(lf2.FaceName, "Arial"); // font style

		// g_pFont 초기화 / 생성
	D3DXCreateFont(Device, 25, 13, 80, 1, FALSE, DEFAULT_CHARSET, OUT_DEFAULT_PRECIS, DEFAULT_QUALITY, DEFAULT_PITCH | FF_DONTCARE, "Arial", &text);


/*	D3DXCreateSprite(Device, &g_pd3dSprite);

	//이미지를 불러오기 위해
	D3DXIMAGE_INFO info;
	//디바이스가 능력을 가진다. 불러올위치
	if (FAILED(D3DXCreateTextureFromFileEx(Device, _T("..\\data\\ball.X"),
		D3DX_DEFAULT_NONPOW2,	// 이미지 가로 크기
		D3DX_DEFAULT_NONPOW2,	// 이미지 세로크기, 자동으로 이미지의 크리게 맞추어 불러준다.
		1,						// Miplavels, 3D 상에서 확대 축소시 사용한다. 깨지는 현상방지를 위해
		0,						// Usage, 사용 용도. 출력 용도로는 0으로 지정
		D3DFMT_A8R8G8B8,		// 이미지 색상 포멧, 각 8bit 사용 이미지
		D3DPOOL_MANAGED,		// pool, 이미지 관리 다이렉트가 직접관리
		D3DX_FILTER_NONE,		// Filter, 확대 축소시 사용하는 필터
		D3DX_FILTER_NONE,		// MipFilter, 확대 축소시 사용하는 필터
		NULL,					// 컬러키설정, 배경을 지울 때 png는 투명을 가진다. 그래서 컬리키 사용안해도 된다.
		&info,					// 불러온 이미지의 정보를 저장 할 구조체
		NULL,					// 팔레트 설정, 8bit인 경우 색상표
		&g_tex))) {				// 생성된 텍스쳐의 주소 반환
		return false;
	}
	else {
		g_tex_width = info.Width;
		g_tex_height = info.Height;
	}*/

	return true;
}

void Cleanup(void)
{
	g_legoPlane.destroy();
	for (int i = 0; i < 4; i++) {
		g_legowall[i].destroy();
	}
	destroyAllLegoBlock();
	g_light.destroy();
	text->Release();
	trace.destroy();
}


// timeDelta represents the time between the current image frame and the last image frame.
// the distance of moving balls should be "velocity * timeDelta"
bool Display(float timeDelta)
{
	int i = 0;
	int j = 0;

	if (Device)
	{
		Device->Clear(0, 0, D3DCLEAR_TARGET | D3DCLEAR_ZBUFFER, 0x00afafaf, 1.0f, 0);
		Device->BeginScene();

	
		//x좌표 y좌표
		text->DrawText(0, "White Score : ", -1, &whiteScoreTextRect, DT_TOP | DT_LEFT, 0xff000000);

		text->DrawText(0, "Yellow Score : ", -1, &yellowScoreTextRect, DT_TOP | DT_LEFT, 0xff000000);

		itoa(whiteScore, whiteScoreText, 10);
		itoa(yellowScore, yellowScoreText, 10);

		text->DrawText(0, whiteScoreText, -1, &whiteScoreRect, DT_TOP | DT_LEFT, 0xff000000);
		text->DrawText(0, yellowScoreText, -1, &yellowScoreRect, DT_TOP | DT_LEFT, 0xff000000);
		dif = difftime(begin, end);

		itoa(whiteHintCount, whiteHintText, 10);
		itoa(yellowHintCount, yellowHintText, 10);

		text->DrawText(0, "White Hint : ", -1, &whiteHintRect, DT_TOP | DT_LEFT, 0xff000000);
		text->DrawText(0, "Yellow Hint : ", -1, &yellowHintRect, DT_TOP | DT_LEFT, 0xff000000);

		text->DrawText(0, whiteHintText, -1, &whiteHintCountRect, DT_TOP | DT_LEFT, 0xff000000);
		text->DrawText(0, yellowHintText, -1, &yellowHintCountRect, DT_TOP | DT_LEFT, 0xff000000);
		

		//text->DrawText(0, ctime(&dif), -1, &timeRect, DT_TOP | DT_LEFT, 0xff000000);
		if ((dif) >= 1 * (playTime + 1)) {
			itoa(++playTime, timeText, 10);
			text->DrawText(0, timeText, -1, &timeRect, DT_TOP | DT_LEFT, 0xff000000);
			
//			srand(time(NULL));
			time(&begin);
		}
		

		// update the position of each ball. during update, check whether each ball hit by walls.
		for (i = 0; i < 4; i++) {
			g_sphere[i].ballUpdate(timeDelta);
			for (j = 0; j < 4; j++) { g_legowall[i].hitBy(g_sphere[j]); }
		}
		

		// check whether any two balls hit together and update the direction of balls
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				if (i >= j) { continue; }
				g_sphere[i].hitBy(g_sphere[j]);
			}
		}

		// Addition
		if ((g_sphere[0].getVelocity_X() == 0 && g_sphere[0].getVelocity_Z() == 0) &&
			(g_sphere[1].getVelocity_X() == 0 && g_sphere[1].getVelocity_Z() == 0) &&
			(g_sphere[2].getVelocity_X() == 0 && g_sphere[2].getVelocity_Z() == 0) &&
			(g_sphere[3].getVelocity_X() == 0 && g_sphere[3].getVelocity_Z() == 0)) {
			isBallMoving = false;
			if (start) {
				if (!playerTurn) {//
					if (g_sphere[3].getpenalty()) {
						if (whiteScore > 0) whiteScore -= SCORE;
					}
					else if (g_sphere[3].getRedBall()[0] && g_sphere[3].getRedBall()[1]) {//
						playerTurn = bWHITE;
						if (g_sphere[3].getpenalty()) {
							playerTurn = bYELLOW;
							if (whiteScore > 0) whiteScore -= SCORE;
						}
						else whiteScore += SCORE;
					}
					else playerTurn = bYELLOW;
				}
				else {
					if (g_sphere[2].getpenalty()) {
						if (yellowScore > 0) yellowScore -= SCORE;
					}
					else if (g_sphere[2].getRedBall()[0] && g_sphere[2].getRedBall()[1]) {
						playerTurn = bYELLOW;
						if (g_sphere[2].getpenalty()) {
							playerTurn = bWHITE;
							if (yellowScore > 0) yellowScore -= SCORE;
						}
						else yellowScore += SCORE;
					}
					else playerTurn = bWHITE;
				}
				for (int i = 0; i < 4; i++) g_sphere[i].allInit();

				start = false;
			}
		}
		else isBallMoving = true;

		// draw plane, walls, and spheres
		g_legoPlane.draw(Device, g_mWorld);
		for (i = 0; i<4; i++) {
			g_legowall[i].draw(Device, g_mWorld);
			g_sphere[i].draw(Device, g_mWorld);
		}
		g_target_blueball.draw(Device, g_mWorld);
		g_light.draw(Device);

		if (hint) {
			trace.ballUpdate(timeDelta);
			if (playerTurn) for(i = 0; i < 3; i ++)trace.hitBy(g_sphere[i]);
			else for (i = 0; i < 3; i++) {
				if (i == 2) i++;
				trace.hitBy(g_sphere[i]);
			}
			for (i = 0; i < 4; i++) g_legowall[i].hitBy(trace);
			trace.draw(Device, g_mWorld);
			if (trace.getVelocity_X() == 0 && trace.getVelocity_Z() == 0) {
				hint = false;
				trace.allInit();
			}
		}
		Device->EndScene();
		Device->Present(0, 0, 0, 0);
		Device->SetTexture(0, NULL);
	}
	time(&end);
	return true;
}

bool whole = false;

LRESULT CALLBACK d3d::WndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	static bool wire = false;
	static bool isReset = true;
	static int old_x = 0;
	static int old_y = 0;
	static enum { WORLD_MOVE, LIGHT_MOVE, BLOCK_MOVE } move = WORLD_MOVE;

	switch (msg) {
	case WM_DESTROY:
	{
		::PostQuitMessage(0);
		break;
	}
	case WM_KEYDOWN:
	{
		switch (wParam) {
		case VK_ESCAPE:
			::DestroyWindow(hwnd);
			break;
		case VK_RETURN:
			if (NULL != Device) {
				wire = !wire;
				Device->SetRenderState(D3DRS_FILLMODE,
					(wire ? D3DFILL_WIREFRAME : D3DFILL_SOLID));
			}
			break;
		case VK_SPACE:

			if (!isBallMoving) {
				start = true;
				if (playerTurn) {
					hint = false;
					D3DXVECTOR3 targetpos = g_target_blueball.getCenter();
					D3DXVECTOR3	whitepos = g_sphere[3].getCenter();
					double theta = acos(sqrt(pow(targetpos.x - whitepos.x, 2)) / sqrt(pow(targetpos.x - whitepos.x, 2) +
						pow(targetpos.z - whitepos.z, 2)));		// 기본 1 사분면
					if (targetpos.z - whitepos.z <= 0 && targetpos.x - whitepos.x >= 0) { theta = -theta; }	//4 사분면
					if (targetpos.z - whitepos.z >= 0 && targetpos.x - whitepos.x <= 0) { theta = PI - theta; } //2 사분면
					if (targetpos.z - whitepos.z <= 0 && targetpos.x - whitepos.x <= 0) { theta = PI + theta; } // 3 사분면
					double distance = sqrt(pow(targetpos.x - whitepos.x, 2) + pow(targetpos.z - whitepos.z, 2));
					g_sphere[3].setPower(distance * cos(theta), (distance * sin(theta)));
					playerTurn = bYELLOW;
				}
				else {
					hint = false;
					D3DXVECTOR3 targetpos = g_target_blueball.getCenter();
					D3DXVECTOR3	yellowpos = g_sphere[2].getCenter();
					double theta = acos(sqrt(pow(targetpos.x - yellowpos.x, 2)) / sqrt(pow(targetpos.x - yellowpos.x, 2) +
						pow(targetpos.z - yellowpos.z, 2)));		// 기본 1 사분면
					if (targetpos.z - yellowpos.z <= 0 && targetpos.x - yellowpos.x >= 0) { theta = -theta; }	//4 사분면
					if (targetpos.z - yellowpos.z >= 0 && targetpos.x - yellowpos.x <= 0) { theta = PI - theta; } //2 사분면
					if (targetpos.z - yellowpos.z <= 0 && targetpos.x - yellowpos.x <= 0) { theta = PI + theta; } // 3 사분면
					double distance = sqrt(pow(targetpos.x - yellowpos.x, 2) + pow(targetpos.z - yellowpos.z, 2));
					g_sphere[2].setPower(distance * cos(theta), distance * sin(theta));
					playerTurn = bWHITE;
				}
			}

			break;
			
		case VK_TAB :
			whole = true;
			break;
		case VK_HINT:	//'A' key press
			if (!isBallMoving) {
				if (!hint) {
					if (playerTurn) {
						if (whiteHintCount > 0) {
							hint = true;
							whiteHintCount--;
							trace.setCenter(g_sphere[3].getCenter().x, g_sphere[3].getCenter().y, g_sphere[3].getCenter().z);
							D3DXVECTOR3 targetpos = g_target_blueball.getCenter();
							D3DXVECTOR3	tracepos = trace.getCenter();
							double theta = acos(sqrt(pow(targetpos.x - tracepos.x, 2)) / sqrt(pow(targetpos.x - tracepos.x, 2) +
								pow(targetpos.z - tracepos.z, 2)));		// 기본 1 사분면
							if (targetpos.z - tracepos.z <= 0 && targetpos.x - tracepos.x >= 0) { theta = -theta; }	//4 사분면
							if (targetpos.z - tracepos.z >= 0 && targetpos.x - tracepos.x <= 0) { theta = PI - theta; } //2 사분면
							if (targetpos.z - tracepos.z <= 0 && targetpos.x - tracepos.x <= 0) { theta = PI + theta; } // 3 사분면
							double distance = sqrt(pow(targetpos.x - tracepos.x, 2) + pow(targetpos.z - tracepos.z, 2));
							trace.setPower(distance * cos(theta), (distance * sin(theta)));

						}
					}
					else {
						if (yellowHintCount > 0) {
							hint = true;
							yellowHintCount--;
							trace.setCenter(g_sphere[2].getCenter());
							D3DXVECTOR3 targetpos = g_target_blueball.getCenter();
							D3DXVECTOR3	tracepos = g_sphere[2].getCenter();
							double theta = acos(sqrt(pow(targetpos.x - tracepos.x, 2)) / sqrt(pow(targetpos.x - tracepos.x, 2) +
								pow(targetpos.z - tracepos.z, 2)));		// 기본 1 사분면
							if (targetpos.z - tracepos.z <= 0 && targetpos.x - tracepos.x >= 0) { theta = -theta; }	//4 사분면
							if (targetpos.z - tracepos.z >= 0 && targetpos.x - tracepos.x <= 0) { theta = PI - theta; } //2 사분면
							if (targetpos.z - tracepos.z <= 0 && targetpos.x - tracepos.x <= 0) { theta = PI + theta; } // 3 사분면
							double distance = sqrt(pow(targetpos.x - tracepos.x, 2) + pow(targetpos.z - tracepos.z, 2));
							trace.setPower(distance * cos(theta), (distance * sin(theta)));
						}
					}
				}
			}
			break;
		case VK_RESET:
			for (int i = 0; i < 4; i++) {
				g_sphere[i].setCenter(spherePos[i][0], (float)M_RADIUS, spherePos[i][1]);
				g_sphere[i].setPower(0, 0);
			}
			whiteScore = 0;
			yellowScore = 0;
			whiteHintCount = 5;
			yellowHintCount = 5;
			playerTurn = bWHITE;
			isBallMoving = false;
			hint = false;
			start = false;
		case VK_S:
			g_target_blueball.setCenter(.0f, (float)M_RADIUS, .0f);
			break;
		}
		break;
	case WM_KEYUP :
		switch (wParam) {
		case VK_TAB:
			whole = false;
			break;
		case VK_HINT:
			
			hint = false;
			trace.allInit();
			break;
		}
		
	}

	case WM_MOUSEMOVE:
	{
		int new_x = LOWORD(lParam);
		int new_y = HIWORD(lParam);
		float dx;
		float dy;

		if (LOWORD(wParam) & MK_LBUTTON) {

			if (isReset) {
				isReset = false;
			}
			else {
				D3DXVECTOR3 vDist;
				D3DXVECTOR3 vTrans;
				D3DXMATRIX mTrans;
				D3DXMATRIX mX;
				D3DXMATRIX mY;

				switch (move) {
				case WORLD_MOVE:
					if (whole) {
						dx = (old_x - new_x) * 0.01f;
						dy = (old_y - new_y) * 0.01f;
						D3DXMatrixRotationY(&mX, dx);
						D3DXMatrixRotationX(&mY, dy);
						g_mWorld = g_mWorld * mX * mY;
					}
					else {
						dy = (old_y - new_y) * 0.01f;
						D3DXMatrixRotationX(&mY, dy);
						g_mWorld = g_mWorld * mY;
					}
					break;
				}
			}
			old_x = new_x;
			old_y = new_y;
		}
		else {
			isReset = true;

			if (LOWORD(wParam) & MK_RBUTTON) {
				dx = (old_x - new_x);// * 0.01f;
				dy = (old_y - new_y);// * 0.01f;

				D3DXVECTOR3 coord3d = g_target_blueball.getCenter();
				g_target_blueball.setCenter(coord3d.x + dx*(-0.007f), coord3d.y, coord3d.z + dy*0.007f);
			}
			old_x = new_x;
			old_y = new_y;

			move = WORLD_MOVE;
		}
		break;
	}
	}

	return ::DefWindowProc(hwnd, msg, wParam, lParam);
}

int WINAPI WinMain(HINSTANCE hinstance,
	HINSTANCE prevInstance,
	PSTR cmdLine,
	int showCmd)
{
	srand(static_cast<unsigned int>(time(NULL)));

	if (!d3d::InitD3D(hinstance,
		Width, Height, true, D3DDEVTYPE_HAL, &Device))
	{
		::MessageBox(0, "InitD3D() - FAILED", 0, 0);
		return 0;
	}

	if (!Setup())
	{
		::MessageBox(0, "Setup() - FAILED", 0, 0);
		return 0;
	}

	d3d::EnterMsgLoop(Display);

	Cleanup();

	Device->Release();

	return 0;
}